package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.Set;
import java.util.UUID;

public interface ITokenJpaDao {
    Set<TokenJpa> findByUserAndValid(UserId userId);

    void markAsRevokedAndExpiredTokens(Set<TokenJpa> userTokens);

    void saveRevokedTokens(Set<TokenJpa> userTokens);

    void save(TokenJpa token);

    UserJpa fromUserGetReferenceToUser(UUID userId);
}
