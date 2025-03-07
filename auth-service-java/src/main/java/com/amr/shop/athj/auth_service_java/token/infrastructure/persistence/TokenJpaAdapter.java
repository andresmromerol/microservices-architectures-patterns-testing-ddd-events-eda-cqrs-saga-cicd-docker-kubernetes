package com.amr.shop.athj.auth_service_java.token.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.ITokenJpaDao;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpa;
import com.amr.shop.athj.auth_service_java.token.domain.ITokenPersistencePort;
import com.amr.shop.athj.auth_service_java.token.domain.TokenModel;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenJpaAdapter implements ITokenPersistencePort {

    private final ITokenJpaDao ITokenJpaDao;
    private final TokenJpaMapper tokenJpaMapper;

    @Autowired
    public TokenJpaAdapter(ITokenJpaDao ITokenJpaDao, TokenJpaMapper tokenJpaMapper) {
        this.ITokenJpaDao = ITokenJpaDao;
        this.tokenJpaMapper = tokenJpaMapper;
    }

    @Override
    public void revokeUserTokens(UserId userId) {
        log.info("Revoking tokens for user: {}", userId.getValue());

        Set<TokenJpa> userTokens = ITokenJpaDao.findByUserAndValid(userId);
        ITokenJpaDao.markAsRevokedAndExpiredTokens(userTokens);
        ITokenJpaDao.saveRevokedTokens(userTokens);
    }

    @Override
    public void save(TokenModel tokenModel) {
        log.info("Saving token for user: {}", tokenModel.getUserId().getValue());
        ITokenJpaDao.save(tokenJpaMapper.toTokenJpa(tokenModel));
    }
}
