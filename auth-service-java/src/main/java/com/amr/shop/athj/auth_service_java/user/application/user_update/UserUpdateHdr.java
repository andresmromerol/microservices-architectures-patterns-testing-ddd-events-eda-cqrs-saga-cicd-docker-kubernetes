package com.amr.shop.athj.auth_service_java.user.application.user_update;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateHdr implements ICommandHandler<UserUpdateCmd> {
  private final UserUpdate userUpdate;

  @Autowired
  public UserUpdateHdr(UserUpdate userUpdate) {
    this.userUpdate = userUpdate;
  }

  @Override
  public void handle(UserUpdateCmd command) {
    userUpdate.execute(
        command.id(),
        command.name(),
        command.email(),
        command.password(),
        command.phone(),
        command.roles(),
        command.status());
  }
}
