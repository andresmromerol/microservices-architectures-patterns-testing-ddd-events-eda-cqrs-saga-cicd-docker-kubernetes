package com.amr.shop.usr.user_context.user.application.create_user;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailQry;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailRes;
import com.amr.shop.usr.user_context.user.domain.IUserFactory;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserAlreadyExistsException;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModelDto;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModelDto;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCreate {

  private final IUserFactory userFactory;
  private final IUserPersistencePort userPersistencePort;
  private final IQueryBus queryBus;

  @Autowired
  public UserCreate(
      IUserFactory userFactory, IUserPersistencePort userPersistencePort, IQueryBus queryBus) {
    this.userFactory = userFactory;
    this.userPersistencePort = userPersistencePort;
    this.queryBus = queryBus;
  }

  private void ensureUserNotExists(String email, RoleEnum role) {
    UserSearchByEmailRes res = queryBus.ask(new UserSearchByEmailQry(email, role));
    if (!res.isEmpty()) {
      throw new UserAlreadyExistsException(email);
    }
  }

  public void executeAdministrator(
      UUID id, String name, String email, String phone, UUID createdByAdminId) {
    ensureUserNotExists(email, RoleEnum.ADMIN);
    UserModel user =
        userFactory.createUserAdministrator(
            new UserAdministratorModelDto(
                id, name, email, UserStatusEnum.ACTIVE, phone, createdByAdminId));
    userPersistencePort.save(user);
  }

  public void executeCustomer(UUID id, String name, String email, String phone, String address) {
    ensureUserNotExists(email, RoleEnum.CUSTOMER);
    UserModel user =
        userFactory.createUserCustomer(
            new UserCustomerModelDto(id, name, email, UserStatusEnum.ACTIVE, phone, address));
    userPersistencePort.save(user);
  }
}
