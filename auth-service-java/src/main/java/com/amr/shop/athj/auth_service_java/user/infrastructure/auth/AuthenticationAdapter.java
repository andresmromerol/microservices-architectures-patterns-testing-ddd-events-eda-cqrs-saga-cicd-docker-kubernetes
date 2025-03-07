package com.amr.shop.athj.auth_service_java.user.infrastructure.auth;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IAuthenticationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationAdapter implements IAuthenticationPort {
    private final AuthenticationManager authenticationManager;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Autowired
    public AuthenticationAdapter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticationToken(String email, String password) {
        log.info("Attempting to authenticate user with email: {}", email);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("Successfully authenticated user with email: {}", email);
        } catch (Exception ex) {
            log.error("Failed to authenticate user with email: {}", email, ex);
            throw new UserAuthAuthenticationFailedException("Failed to authenticate with email: " + email);
        }
    }

    @Override
    public String getSecretKey() {
        return this.secretKey;
    }

    @Override
    public long getJwtExpiration() {
        return this.jwtExpiration;
    }

    @Override
    public long refreshExpiration() {
        return this.refreshExpiration;
    }
}
