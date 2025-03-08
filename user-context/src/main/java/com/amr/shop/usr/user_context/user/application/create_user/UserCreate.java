package com.amr.shop.usr.user_context.user.application.create_user;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailQry;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailRes;
import com.amr.shop.usr.user_context.user.domain.*;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModelDto;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModelDto;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

  private void ensureUserNotExists(String email) {
    UserSearchByEmailRes res = queryBus.ask(new UserSearchByEmailQry(email));
    if (!res.isEmpty()) {
      throw new UserAlreadyExistsException(email);
    }
  }

  public void executeAdministrator(
      UUID id, String name, String email, String phone, UUID createdByAdminId) {
    ensureUserNotExists(email);
    UserModel user =
        userFactory.createUserAdministrator(
            new UserAdministratorModelDto(
                id, name, email, UserStatusEnum.ACTIVE, phone, createdByAdminId));
    userPersistencePort.save(user);
  }

  public void executeCustomer(UUID id, String name, String email, String phone, String address) {
    ensureUserNotExists(email);
    UserModel user =
        userFactory.createUserCustomer(
            new UserCustomerModelDto(id, name, email, UserStatusEnum.ACTIVE, phone, address));
    userPersistencePort.save(user);
  }
}
