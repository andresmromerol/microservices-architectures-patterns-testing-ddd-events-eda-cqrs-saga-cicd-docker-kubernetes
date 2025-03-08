package com.amr.shop.athj.auth_service_java.user.application.user_update;

import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserUpdate {
  private final IUserAuthPersistencePort userUpdatePort;

  @Autowired
  public UserUpdate(IUserAuthPersistencePort userUpdatePort) {
    this.userUpdatePort = userUpdatePort;
  }

  public void execute(
      UUID id,
      String name,
      String email,
      String password,
      String phone,
      Set<RoleEnum> roles,
      UserStatusEnum status) {
    log.info("Starting user update for user with id: {}", id);
    UserModel user = UserModel.create(id, name, email, password, status, phone, roles);
    userUpdatePort.update(user);
    log.info("User updated successfully with id: {}", id);
  }
}
