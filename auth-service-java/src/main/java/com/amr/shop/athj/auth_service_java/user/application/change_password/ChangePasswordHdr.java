package com.amr.shop.athj.auth_service_java.user.application.change_password;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordHdr implements ICommandHandler<ChangePasswordCmd> {
  private final ChangePassword changePassword;

  @Autowired
  public ChangePasswordHdr(ChangePassword changePassword) {
    this.changePassword = changePassword;
  }

  @Override
  public void handle(ChangePasswordCmd command) {
    changePassword.execute(
        command.email(),
        command.currentPassword(),
        command.newPassword(),
        command.confirmationPassword());
  }
}
