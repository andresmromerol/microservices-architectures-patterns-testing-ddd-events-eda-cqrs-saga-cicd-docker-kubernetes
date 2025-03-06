package com.amr.shop.athj.auth_service_java.user.application.user_register;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuthRegisterHdr implements ICommandHandler<UserAuthRegisterCmd> {
    private final UserAuthRegister userAuthRegister;

    @Autowired
    public UserAuthRegisterHdr(UserAuthRegister userAuthRegister) {
        this.userAuthRegister = userAuthRegister;
    }

    @Override
    public void handle(UserAuthRegisterCmd command) {
        userAuthRegister.execute(
                command.id(), command.name(), command.email(), command.password(), command.phone(), command.roles());
    }
}
