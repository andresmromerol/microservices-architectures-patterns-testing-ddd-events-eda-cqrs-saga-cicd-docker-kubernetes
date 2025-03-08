package com.amr.shop.athj.auth_service_java.user.application.user_update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserUpdateTest {

  @Mock private IUserAuthPersistencePort userUpdatePort;

  private UserUpdate userUpdate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userUpdate = new UserUpdate(userUpdatePort);
  }

  @Test
  void shouldUpdateUser() {
    UUID id = UUID.randomUUID();
    String name = "andres";
    String email = "andres@email.com";
    String password = "123456789";
    String phone = "3209118911";
    Set<RoleEnum> roles = new HashSet<>();
    roles.add(RoleEnum.USER);
    UserStatusEnum status = UserStatusEnum.ACTIVE;
    userUpdate.execute(id, name, email, password, phone, roles, status);
    verify(userUpdatePort).update(any(UserModel.class));
  }
}
