package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.IUserJpaDao;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.token.domain.TokenRevocationFailedException;
import com.amr.shop.athj.auth_service_java.token.domain.TokenSaveFailedException;
import com.amr.shop.athj.auth_service_java.token.domain.ValidTokenNotFoundException;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenJpaImpl implements ITokenJpaDao {

    private final TokenJpaRepository tokenRepository;
    private final IUserJpaDao userJpaDao;

    @Autowired
    public TokenJpaImpl(TokenJpaRepository tokenRepository, IUserJpaDao userJpaDao) {
        this.tokenRepository = tokenRepository;
        this.userJpaDao = userJpaDao;
    }

    @Override
    public Set<TokenJpa> findByUserAndValid(UserId userId) {
        try {
            return tokenRepository.findAllValidTokenByUser(userId.getValue());
        } catch (Exception ex) {
            log.error("Failed to find valid tokens for user: {}", userId.getValue(), ex);
            throw new ValidTokenNotFoundException(userId.getValue());
        }
    }

    public void markAsRevokedAndExpiredTokens(Set<TokenJpa> userTokens) {
        if (userTokens.isEmpty()) return;
        userTokens.forEach(this::markAsRevokedAndExpiredToken);
    }

    public void markAsRevokedAndExpiredToken(TokenJpa token) {
        try {
            if (token != null) {
                token.setExpired(true);
                token.setRevoked(true);
            }
        } catch (Exception ex) {
            log.error(
                    "Failed to revoke and expire token for user: {}",
                    token.getUser().getId(),
                    ex);
            throw new TokenRevocationFailedException(token.getUser().getId());
        }
    }

    @Override
    public void saveRevokedTokens(Set<TokenJpa> userTokens) {
        try {
            tokenRepository.saveAll(userTokens);
        } catch (Exception ex) {
            log.error("Failed to save revoked tokens", ex);
            throw new TokenSaveFailedException();
        }
    }

    @Override
    public void save(TokenJpa token) {
        log.info("Saving token for user: {}", token.getUser().getId());
        tokenRepository.save(token);
    }

    @Override
    public UserJpa fromUserGetReferenceToUser(UUID userId) {
        log.info("Getting reference to user: {}", userId);
        return userJpaDao.getReferenceToUser(userId);
    }
}
