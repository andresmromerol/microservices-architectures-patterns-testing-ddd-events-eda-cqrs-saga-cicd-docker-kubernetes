package com.amr.shop.athj.auth_service_java.token.application.token_generate;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenGenerate {
    private final ITokenPort tokenPort;

    @Autowired
    public TokenGenerate(ITokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    public TokenGenerateRes execute(BuildTokenDto token, String secretKey) {

        log.info("Generating token for user {}", token.getUsername());
        String generatedToken = tokenPort.generateToken(token, secretKey);
        log.info("Token generated successfully for user {}", token.getUsername());

        return new TokenGenerateRes(generatedToken);
    }
}
