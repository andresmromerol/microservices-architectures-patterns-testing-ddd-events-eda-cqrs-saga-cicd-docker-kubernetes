package com.amr.shop.athj.auth_service_java.user.application.authentication_token;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IAuthenticationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationToken {
    private final IAuthenticationPort authenticationPort;

    public AuthenticationToken(IAuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    public void execute(String email, String password) {
        log.info("Attempting to authenticate user with email: {}", email);
        try {
            authenticationPort.authenticationToken(email, password);
            log.info("Successfully authenticated user with email: {}", email);
        } catch (Exception ex) {
            log.error("Failed to authenticate user with email: {}", email, ex);
            throw new UserAuthAuthenticationFailedException("Failed to authenticate with email: " + email);
        }
    }
}
