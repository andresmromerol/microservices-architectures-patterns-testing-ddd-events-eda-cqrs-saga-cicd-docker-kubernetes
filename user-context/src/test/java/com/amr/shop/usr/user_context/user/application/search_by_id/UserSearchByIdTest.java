package com.amr.shop.usr.user_context.user.application.search_by_id;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserNullModel;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserSearchByIdTest {

  @Mock private IUserPersistencePort userPersistencePort;

  @InjectMocks private UserSearchById userSearchById;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void execute_WhenUserExists_ShouldReturnUserSearchByEmailRes() {
    String email = "andres@email.com";
    UUID userId = UUID.randomUUID();
    String name = "user";
    String phone = "3209118911";
    UserAdministratorModel userModel = mock(UserAdministratorModel.class);
    UserId userIdObj = mock(UserId.class);
    when(userIdObj.getValue()).thenReturn(userId);
    when(userModel.getId()).thenReturn(userIdObj);
    when(userModel.getName()).thenReturn(name);
    when(userModel.getEmail()).thenReturn(email);
    when(userModel.getStatus()).thenReturn(UserStatusEnum.ACTIVE);
    when(userModel.getPhone()).thenReturn(phone);
    when(userModel.isNullModel()).thenReturn(false);
    when(userPersistencePort.findByEmail(any(EmailVo.class))).thenReturn(Optional.of(userModel));
    UserSearchByEmailRes result = userSearchById.execute(email);
    assertNotNull(result);
    assertEquals(userId, result.id());
    assertEquals(name, result.name());
    assertEquals(email, result.email());
    assertEquals(UserStatusEnum.ACTIVE, result.status());
    assertEquals(phone, result.phone());
    assertFalse(result.isEmpty());
    verify(userPersistencePort).findByEmail(any(EmailVo.class));
  }

  @Test
  void execute_WhenUserDoesNotExist_ShouldReturnEmptyResponse() {
    String email = "andres@email.com";
    UserNullModel nullModel = new UserNullModel();
    when(userPersistencePort.findByEmail(any(EmailVo.class))).thenReturn(Optional.of(nullModel));
    UserSearchByEmailRes result = userSearchById.execute(email);
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(userPersistencePort).findByEmail(any(EmailVo.class));
  }
}
