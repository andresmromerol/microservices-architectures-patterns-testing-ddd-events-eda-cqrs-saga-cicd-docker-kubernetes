package com.amr.shop.athj.auth_service_java.user.application.user_register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordQry;
import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordRes;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthEmailAlreadyExistsException;
import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserAuthRegisterTest {

  @Mock private IUserAuthPersistencePort userAuthPersistencePort;

  @Mock private IQueryBus queryBus;

  private UserAuthRegister userAuthRegister;

  private final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
  private final String userName = "andres";
  private final String userEmail = "andres@email.com";
  private final String userPassword = "123456789";
  private final String userPhone = "3209118911";
  private final Set<RoleEnum> userRoles = new HashSet<>(Set.of(RoleEnum.USER));

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userAuthRegister = new UserAuthRegister(userAuthPersistencePort, queryBus);
  }

  @Test
  void shouldRegisterUserSuccessfully() {
    UserSearchByEmailRes emptyEmailResponse =
        UserSearchByEmailRes.builder()
            .id(null)
            .name(null)
            .email(null)
            .password(null)
            .phone(null)
            .roles(null)
            .permissions(null)
            .isEmpty(true)
            .build();
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyEmailResponse);
    when(queryBus.ask(any(EncryptPasswordQry.class)))
        .thenReturn(new EncryptPasswordRes("123456789"));
    userAuthRegister.execute(userId, userName, userEmail, userPassword, userPhone, userRoles);
    ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
    verify(userAuthPersistencePort).save(userCaptor.capture());
    UserModel savedUser = userCaptor.getValue();
    assertEquals(userId, savedUser.getId().getValue());
    assertEquals(userName, savedUser.getName().getValue());
    assertEquals(userEmail, savedUser.getEmail().getValue());
    assertEquals("123456789", savedUser.getPassword().getValue());
    assertEquals(userPhone, savedUser.getPhone().getValue());
    assertEquals(userRoles, savedUser.getRoles());
  }

  @Test
  void shouldThrowExceptionWhenEmailExists() {
    UserSearchByEmailRes existingUserResponse =
        UserSearchByEmailRes.builder()
            .id(UUID.randomUUID())
            .name("andres")
            .email(userEmail)
            .password("123456789")
            .phone("3209118911")
            .roles(Set.of(RoleEnum.USER))
            .permissions(Set.of())
            .isEmpty(false)
            .build();
    UserAuthEmailAlreadyExistsException userAuthEmailAlreadyExistsException =
        new UserAuthEmailAlreadyExistsException(userEmail);
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(existingUserResponse);
    UserAuthEmailAlreadyExistsException exception =
        assertThrows(
            UserAuthEmailAlreadyExistsException.class,
            () ->
                userAuthRegister.execute(
                    userId, userName, userEmail, userPassword, userPhone, userRoles));
    assertEquals(userAuthEmailAlreadyExistsException.getMessage(), exception.getMessage());
    verify(userAuthPersistencePort, never()).save(any());
  }

  @Test
  void shouldEncryptPasswordCorrectly() {
    UserSearchByEmailRes emptyEmailResponse =
        UserSearchByEmailRes.builder()
            .id(null)
            .name(null)
            .email(null)
            .password(null)
            .phone(null)
            .roles(null)
            .permissions(null)
            .isEmpty(true)
            .build();
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyEmailResponse);
    when(queryBus.ask(any(EncryptPasswordQry.class)))
        .thenReturn(new EncryptPasswordRes("123456789"));
    userAuthRegister.execute(userId, userName, userEmail, userPassword, userPhone, userRoles);
    ArgumentCaptor<EncryptPasswordQry> encryptCaptor =
        ArgumentCaptor.forClass(EncryptPasswordQry.class);
    verify(queryBus).ask(encryptCaptor.capture());
    assertEquals(userPassword, encryptCaptor.getValue().password());
    ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
    verify(userAuthPersistencePort).save(userCaptor.capture());
    assertEquals("123456789", userCaptor.getValue().getPassword().getValue());
  }

  @Test
  void shouldVerifyEmailBeforeRegistering() {
    UserSearchByEmailRes emptyEmailResponse =
        UserSearchByEmailRes.builder()
            .id(null)
            .name(null)
            .email(null)
            .password(null)
            .phone(null)
            .roles(null)
            .permissions(null)
            .isEmpty(true)
            .build();
    when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyEmailResponse);
    when(queryBus.ask(any(EncryptPasswordQry.class)))
        .thenReturn(new EncryptPasswordRes("123456789"));
    userAuthRegister.execute(userId, userName, userEmail, userPassword, userPhone, userRoles);
    ArgumentCaptor<UserSearchByEmailQry> emailCaptor =
        ArgumentCaptor.forClass(UserSearchByEmailQry.class);
    verify(queryBus).ask(emailCaptor.capture());
    assertEquals(userEmail, emailCaptor.getValue().email());
    InOrder inOrder = inOrder(queryBus, userAuthPersistencePort);
    inOrder.verify(queryBus).ask(any(UserSearchByEmailQry.class));
    inOrder.verify(queryBus).ask(any(EncryptPasswordQry.class));
    inOrder.verify(userAuthPersistencePort).save(any(UserModel.class));
  }
}
