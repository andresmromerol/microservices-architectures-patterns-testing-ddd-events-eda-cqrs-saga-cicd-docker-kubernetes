package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator.UserAdministratorJpa;
import com.amr.shop.usr.user_context.user.domain.IUserVisitor;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

class UserRepositoryJpaTest {

  @Mock private EntityManager entityManager;

  @Mock private TypedQuery<UserJpa> query;

  @InjectMocks private UserRepositoryJpa userRepositoryJpa;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(userRepositoryJpa, "entityManager", entityManager);
  }

  @Test
  void save_ShouldPersistUserJpa() {
    UserAdministratorModel userModel = mock(UserAdministratorModel.class);
    UserId userId = mock(UserId.class);
    UUID userUuid = UUID.randomUUID();
    when(userId.getValue()).thenReturn(userUuid);
    when(userModel.getId()).thenReturn(userId);
    when(userModel.getName()).thenReturn("andres");
    when(userModel.getEmail()).thenReturn("andres@email.com");
    when(userModel.getStatus()).thenReturn(UserStatusEnum.ACTIVE);
    when(userModel.getPhone()).thenReturn("3209118911");
    when(userModel.getCreatedByAdminId()).thenReturn(UUID.randomUUID());
    doAnswer(
            (Answer<Void>)
                invocation -> {
                  IUserVisitor visitor = invocation.getArgument(0);
                  visitor.visit((UserAdministratorModel) userModel);
                  return null;
                })
        .when(userModel)
        .accept(any(IUserVisitor.class));
    userRepositoryJpa.save(userModel);
    verify(entityManager).persist(any(UserJpa.class));
  }

  @Test
  void findByEmail_WhenUserExists_ShouldReturnUserModel() {
    EmailVo email = new EmailVo("user@email.com");
    UserAdministratorJpa userJpa = new UserAdministratorJpa();
    userJpa.setId(UUID.randomUUID());
    userJpa.setEmail("user@email.com");
    userJpa.setName("user");
    userJpa.setPhone("3209118911");
    userJpa.setStatus(UserStatusEnum.ACTIVE);
    userJpa.setCreatedByAdminId(UUID.randomUUID());
    when(entityManager.createQuery(anyString(), eq(UserJpa.class))).thenReturn(query);
    when(query.setParameter(anyString(), anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(userJpa);
    Optional<UserModel> result = userRepositoryJpa.findByEmail(email);
    assertTrue(result.isPresent());
    assertFalse(result.get().isNullModel());
    assertEquals(userJpa.getEmail(), result.get().getEmail());
  }

  @Test
  void findByEmail_WhenUserDoesNotExist_ShouldReturnUserNullModel() {
    EmailVo email = new EmailVo("andres@email.com");
    when(entityManager.createQuery(anyString(), eq(UserJpa.class))).thenReturn(query);
    when(query.setParameter(anyString(), anyString())).thenReturn(query);
    when(query.getSingleResult()).thenThrow(new NoResultException("User not found"));
    Optional<UserModel> result = userRepositoryJpa.findByEmail(email);
    assertTrue(result.isPresent());
    assertTrue(result.get().isNullModel());
  }
}
