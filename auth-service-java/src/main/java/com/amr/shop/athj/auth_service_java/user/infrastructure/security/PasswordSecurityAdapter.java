package com.amr.shop.athj.auth_service_java.user.infrastructure.security;

import com.amr.shop.athj.auth_service_java.user.domain.ports.IPasswordPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PasswordSecurityAdapter implements IPasswordPort {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordSecurityAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        log.debug("Encoding password");
        String encodedPassword = passwordEncoder.encode(password);
        log.debug("Password encoded successfully");
        return encodedPassword;
    }
}
