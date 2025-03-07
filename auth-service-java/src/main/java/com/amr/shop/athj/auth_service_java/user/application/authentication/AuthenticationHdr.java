package com.amr.shop.athj.auth_service_java.user.application.authentication;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationHdr implements IQueryHandler<AuthenticatorQry, AuthenticatorRes> {
    private final Authentication authentication;

    public AuthenticationHdr(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public AuthenticatorRes handle(AuthenticatorQry query) {
        return authentication.execute(query.email());
    }
}
