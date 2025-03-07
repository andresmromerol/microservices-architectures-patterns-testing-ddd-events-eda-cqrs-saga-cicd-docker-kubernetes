package com.amr.shop.athj.auth_service_java.token.domain;

import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;

public interface ITokenPersistencePort {
    void revokeUserTokens(UserId userId);

    void save(TokenModel tokenModel);
}
