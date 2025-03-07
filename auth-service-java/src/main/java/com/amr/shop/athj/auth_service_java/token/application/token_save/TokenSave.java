package com.amr.shop.athj.auth_service_java.token.application.token_save;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPersistencePort;
import com.amr.shop.athj.auth_service_java.token.domain.TokenModel;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenId;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenType;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenSave {

    private final ITokenPersistencePort tokenPersistencePort;

    @Autowired
    public TokenSave(ITokenPersistencePort tokenPersistencePort) {
        this.tokenPersistencePort = tokenPersistencePort;
    }

    public void execute(UUID userId, String token) {
        log.info("Saving token for user: {}", userId);

        TokenModel tokenModel = TokenModel.create(
                new TokenId(UUID.randomUUID()), token, TokenType.BEARER, false, false, new UserId(userId));
        tokenPersistencePort.save(tokenModel);

        log.info("Token saved successfully for user: {}", userId);
    }
}
