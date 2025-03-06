package com.amr.shop.athj.auth_service_java.user.application.authentication_token;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenHdr implements ICommandHandler<AuthenticationTokenCmd> {
    private AuthenticationToken authenticationToken;

    @Autowired
    public AuthenticationTokenHdr(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    public void handle(AuthenticationTokenCmd command) {
        authenticationToken.execute(command.email(), command.password());
    }
}
