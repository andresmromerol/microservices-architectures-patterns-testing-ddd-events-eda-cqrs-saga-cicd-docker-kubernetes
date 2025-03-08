package com.amr.shop.athj.auth_service_java.user.application.change_password;

import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordQry;
import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordRes;
import com.amr.shop.athj.auth_service_java.user.application.user_update.UserUpdateCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthNewPasswordConfirmationInvalidException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthPasswordConfirmationInvalidException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IPasswordPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChangePassword {
  private final IQueryBus queryBus;
  private final ICommandBus commandBus;
  private final IPasswordPort passwordPort;

  @Autowired
  public ChangePassword(IQueryBus queryBus, ICommandBus commandBus, IPasswordPort passwordPort) {
    this.queryBus = queryBus;
    this.commandBus = commandBus;
    this.passwordPort = passwordPort;
  }

  public void execute(
      String email, String currentPassword, String newPassword, String confirmationPassword) {
    log.debug("Executing change password for email: {}", email);
    UserSearchByEmailRes user = getUser(email);
    ensurePasswordMatch(currentPassword, user.password());
    ensureNewPasswordMatch(newPassword, confirmationPassword);
    EncryptPasswordRes encryptedPassword = queryBus.ask(new EncryptPasswordQry(newPassword));
    updateUser(user, encryptedPassword);
    log.debug("Password change executed successfully for email: {}", email);
  }

  private void updateUser(UserSearchByEmailRes user, EncryptPasswordRes encryptedPassword) {
    log.debug("Updating user with id: {}", user.id());
    commandBus.dispatch(
        new UserUpdateCmd(
            user.id(),
            user.name(),
            user.email(),
            encryptedPassword.encryptedPassword(),
            user.phone(),
            user.roles(),
            UserStatusEnum.ACTIVE));
    log.debug("User updated successfully with id: {}", user.id());
  }

  private void ensureNewPasswordMatch(String newPassword, String confirmationPassword) {
    log.debug("Ensuring new password matches confirmation password");
    if (!newPassword.equals(confirmationPassword)) {
      log.error("New password and confirmation password do not match");
      throw new UserAuthPasswordConfirmationInvalidException();
    }
  }

  private void ensurePasswordMatch(String currentPassword, String password) {
    log.debug("Ensuring current password matches stored password");
    if (!passwordPort.matches(currentPassword, password)) {
      log.error("Current password does not match stored password");
      throw new UserAuthNewPasswordConfirmationInvalidException();
    }
  }

  private UserSearchByEmailRes getUser(String email) {
    log.debug("Retrieving user by email: {}", email);
    return queryBus.ask(new UserSearchByEmailQry(email));
  }
}
