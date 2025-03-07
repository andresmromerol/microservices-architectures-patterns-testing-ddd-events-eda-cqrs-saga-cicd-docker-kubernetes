package com.amr.shop.athj.auth_service_java.token.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.ITokenJpaDao;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.token.domain.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenJpaMapper {
    private final ITokenJpaDao tokenJpaDao;

    @Autowired
    public TokenJpaMapper(ITokenJpaDao tokenJpaDao) {
        this.tokenJpaDao = tokenJpaDao;
    }

    public TokenJpa toTokenJpa(TokenModel tokenModel) {
        UserJpa user =
                tokenJpaDao.fromUserGetReferenceToUser(tokenModel.getUserId().getValue());
        return TokenJpa.builder()
                .id(tokenModel.getId().getValue())
                .token(tokenModel.getToken())
                .tokenType(tokenModel.getTokenType())
                .expired(tokenModel.isExpired())
                .revoked(tokenModel.isRevoked())
                .user(user)
                .build();
    }
}
