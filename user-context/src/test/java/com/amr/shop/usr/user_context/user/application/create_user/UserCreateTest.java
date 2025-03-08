package com.amr.shop.usr.user_context.user.application.create_user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailQry;
import com.amr.shop.usr.user_context.user.application.search_by_id.UserSearchByEmailRes;
import com.amr.shop.usr.user_context.user.domain.IUserFactory;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserAlreadyExistsException;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserCreateTest {

  @Mock private IUserFactory userFactory;

  @Mock private IUserPersistencePort userPersistencePort;

  @Mock private IQueryBus queryBus;

  @Mock private UserModel userModel;

  private UserCreate userCreate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userCreate = new UserCreate(userFactory, userPersistencePort, queryBus);
  }

  @Test
  void executeAdministrator_ShouldCreateAndSaveAdminUser() {
    UUID id = UUID.randomUUID();
    String name = "user";
    String email = "user@email.com";
    String phone = "3209118911";
    UUID createdByAdminId = UUID.randomUUID();
    UserSearchByEmailRes emptyResponse =
        new UserSearchByEmailRes(null, null, null, null, null, true);
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyResponse);
    when(userFactory.createUserAdministrator(any())).thenReturn(userModel);
    userCreate.executeAdministrator(id, name, email, phone, createdByAdminId);
    verify(queryBus).ask(any(UserSearchByEmailQry.class));
    verify(userFactory).createUserAdministrator(any());
    verify(userPersistencePort).save(userModel);
  }

  @Test
  void executeAdministrator_WhenUserExists_ShouldThrowUserAlreadyExistsException() {
    UUID id = UUID.randomUUID();
    String name = "user";
    String email = "user@email.com";
    String phone = "3209118911";
    UUID createdByAdminId = UUID.randomUUID();
    UserSearchByEmailRes existingUserResponse =
        new UserSearchByEmailRes(
            UUID.randomUUID(), "user", email, UserStatusEnum.ACTIVE, "3209118911", false);
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(existingUserResponse);
    assertThrows(
        UserAlreadyExistsException.class,
        () -> userCreate.executeAdministrator(id, name, email, phone, createdByAdminId));
    verify(queryBus).ask(any(UserSearchByEmailQry.class));
    verify(userFactory, never()).createUserAdministrator(any());
    verify(userPersistencePort, never()).save(any());
  }

  @Test
  void executeCustomer_ShouldCreateAndSaveCustomerUser() {
    UUID id = UUID.randomUUID();
    String name = "user";
    String email = "user@email.com";
    String phone = "3209118911";
    String address = "address";
    UserSearchByEmailRes emptyResponse =
        new UserSearchByEmailRes(null, null, null, null, null, true);
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyResponse);
    when(userFactory.createUserCustomer(any())).thenReturn(userModel);
    userCreate.executeCustomer(id, name, email, phone, address);
    verify(queryBus).ask(any(UserSearchByEmailQry.class));
    verify(userFactory).createUserCustomer(any());
    verify(userPersistencePort).save(userModel);
  }

  @Test
  void executeCustomer_WhenUserExists_ShouldThrowUserAlreadyExistsException() {
    UUID id = UUID.randomUUID();
    String name = "user";
    String email = "user@email.com";
    String phone = "3209118911";
    String address = "address";
    UserSearchByEmailRes existingUserResponse =
        new UserSearchByEmailRes(
            UUID.randomUUID(), "user", email, UserStatusEnum.ACTIVE, "3209118911", false);
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(existingUserResponse);
    assertThrows(
        UserAlreadyExistsException.class,
        () -> userCreate.executeCustomer(id, name, email, phone, address));
    verify(queryBus).ask(any(UserSearchByEmailQry.class));
    verify(userFactory, never()).createUserCustomer(any());
    verify(userPersistencePort, never()).save(any());
  }
}
