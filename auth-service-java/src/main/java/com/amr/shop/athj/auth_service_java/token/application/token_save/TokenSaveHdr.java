package com.amr.shop.athj.auth_service_java.token.application.token_save;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenSaveHdr implements ICommandHandler<TokenSaveCmd> {

    private final TokenSave tokenSave;

    @Autowired
    public TokenSaveHdr(TokenSave tokenSave) {
        this.tokenSave = tokenSave;
    }

    @Override
    public void handle(TokenSaveCmd command) {
        tokenSave.execute(command.userId(), command.token());
    }
}
