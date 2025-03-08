package com.amr.shop.usr.user_context.user.application.create_user.customer;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import com.amr.shop.usr.user_context.user.application.create_user.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCreateCustomerHdr implements ICommandHandler<UserCreateCustomerCmd> {
  private final UserCreate userCreate;

  @Autowired
  public UserCreateCustomerHdr(UserCreate userCreate) {
    this.userCreate = userCreate;
  }

  @Override
  public void handle(UserCreateCustomerCmd command) {
    userCreate.executeCustomer(
        command.id(), command.name(), command.email(), command.phone(), command.address());
  }
}
