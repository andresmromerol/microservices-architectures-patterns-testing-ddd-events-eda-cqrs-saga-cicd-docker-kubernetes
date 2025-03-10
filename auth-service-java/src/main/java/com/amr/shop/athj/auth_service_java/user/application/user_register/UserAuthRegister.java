package com.amr.shop.athj.auth_service_java.user.application.user_register;

import static com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum.ADMIN;
import static com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum.CUSTOMER;

import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordQry;
import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordRes;
import com.amr.shop.athj.auth_service_java.user.domain.AdministratorExtra;
import com.amr.shop.athj.auth_service_java.user.domain.AuthUserExtraInformation;
import com.amr.shop.athj.auth_service_java.user.domain.CustomerExtra;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthModel;
import com.amr.shop.athj.auth_service_java.user.domain.exception.UserAuthEmailAlreadyExistsException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBusEventDistributor;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAuthRegister {
  private final IUserAuthPersistencePort userAuthPersistencePort;
  private final IQueryBus queryBus;
  private final IEventBusEventDistributor eventBusEventConfiguration;

  @Autowired
  public UserAuthRegister(
      IUserAuthPersistencePort userAuthPersistencePort,
      IQueryBus queryBus,
      IEventBusEventDistributor eventBusEventConfiguration) {
    this.userAuthPersistencePort = userAuthPersistencePort;
    this.queryBus = queryBus;
    this.eventBusEventConfiguration = eventBusEventConfiguration;
  }

  public void execute(
      UUID id,
      String name,
      String email,
      String password,
      String phone,
      Set<RoleEnum> roles,
      UUID createdByAdminId,
      String address) {

    log.debug("Executing user auth register with id: {}", id);
    ensureNotEmailExists(email);
    log.debug("Creating user auth model with id: {}", id);
    Set<AuthUserExtraInformation> authUserExtraInformation =
        getInformationExtra(roles, address, createdByAdminId);
    UserAuthModel auth =
        UserAuthModel.create(
            id,
            name,
            email,
            getEncryptedPasswords(password),
            UserStatusEnum.ACTIVE,
            phone,
            roles,
            authUserExtraInformation);
    log.debug("Saving user auth model with id: {}", id);
    userAuthPersistencePort.save(auth);
    eventBusEventConfiguration.publish(auth.pullDomainEvents());
  }

  private Set<AuthUserExtraInformation> getInformationExtra(
      Set<RoleEnum> roles, String address, UUID createdByAdminId) {
    log.debug("Getting user extra information for roles: {}", roles);
    return roles.stream()
        .map(
            role -> {
              AuthUserExtraInformation informationExtra = null;
              if (role.equals(ADMIN)) {
                log.debug(
                    "Creating auth extra information for admin with id: {}", createdByAdminId);
                informationExtra =
                    new AuthUserExtraInformation(new AdministratorExtra(createdByAdminId), role);
              } else if (role.equals(CUSTOMER)) {
                log.debug("Creating auth extra information for customer with address: {}", address);
                informationExtra = new AuthUserExtraInformation(new CustomerExtra(address), role);
              }
              return informationExtra;
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  private String getEncryptedPasswords(String password) {
    log.debug("Getting encrypted passwords for password: {}", password);
    EncryptPasswordRes res = queryBus.ask(new EncryptPasswordQry(password));
    return res.encryptedPassword();
  }

  private void ensureNotEmailExists(String email) {
    log.debug("Ensuring not email exists with email: {}", email);
    UserSearchByEmailRes ask = queryBus.ask(new UserSearchByEmailQry(email));
    if (!ask.isEmpty()) {
      log.error("Email already exists with email: {}", email);
      throw new UserAuthEmailAlreadyExistsException(email);
    }
  }
}
