package com.amr.shop.athj.auth_service_java.token.application.token_revoke;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPersistencePort;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenRevoke {
    private final ITokenPersistencePort tokenRevokePort;

    @Autowired
    public TokenRevoke(ITokenPersistencePort tokenRevokePort) {
        this.tokenRevokePort = tokenRevokePort;
    }

    public void execute(UUID userId) {
        log.info("Revoking all tokens for user: {}", userId);
        tokenRevokePort.revokeUserTokens(new UserId(userId));
        log.info("Tokens for user: {} have been successfully revoked", userId);
    }
}
