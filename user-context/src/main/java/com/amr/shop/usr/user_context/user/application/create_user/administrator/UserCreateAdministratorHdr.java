package com.amr.shop.usr.user_context.user.application.create_user.administrator;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import com.amr.shop.usr.user_context.user.application.create_user.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCreateAdministratorHdr implements ICommandHandler<UserCreateAdministratorCmd> {
  private final UserCreate userCreate;

  @Autowired
  public UserCreateAdministratorHdr(UserCreate userCreate) {
    this.userCreate = userCreate;
  }

  @Override
  public void handle(UserCreateAdministratorCmd command) {
    userCreate.executeAdministrator(
        command.id(), command.name(), command.email(), command.phone(), command.createdByAdminId());
  }
}
