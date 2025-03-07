package com.amr.shop.athj.auth_service_java.token.application.token_revoke;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRevokeHdr implements ICommandHandler<TokenRevokeCmd> {

    private final TokenRevoke tokenRevoke;

    @Autowired
    public TokenRevokeHdr(TokenRevoke tokenRevoke) {
        this.tokenRevoke = tokenRevoke;
    }

    @Override
    public void handle(TokenRevokeCmd c) {
        tokenRevoke.execute(c.userId());
    }
}
