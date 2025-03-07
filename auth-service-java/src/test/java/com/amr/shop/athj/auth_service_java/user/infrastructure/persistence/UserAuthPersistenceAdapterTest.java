package com.amr.shop.athj.auth_service_java.user.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpaRepository;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserAuthPersistenceAdapterTest {

    private UserAuthPersistenceAdapter userAuthPersistenceAdapter;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserPersistenceMapper userPersistenceMapper;

    private static final UUID USER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final String NAME = "andres";
    private static final String EMAIL = "andres@mail.com";
    private static final String PASSWORD = "123456789";
    private static final String PHONE = "3209118911";
    private static final UserStatusEnum STATUS = UserStatusEnum.ACTIVE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAuthPersistenceAdapter = new UserAuthPersistenceAdapter(userJpaRepository, userPersistenceMapper);
    }

    @Test
    void shouldSaveUserSuccessfully() {

        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);

        UserModel userModel = UserModel.create(USER_ID, NAME, EMAIL, PASSWORD, STATUS, PHONE, roles);

        UserJpa userJpa = UserJpa.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .status(STATUS)
                .phone(PHONE)
                .roles(roles)
                .build();

        when(userPersistenceMapper.modelToJpa(userModel)).thenReturn(userJpa);

        userAuthPersistenceAdapter.save(userModel);

        verify(userPersistenceMapper).modelToJpa(userModel);
        verify(userJpaRepository).save(userJpa);
    }

    @Test
    void shouldUpdateExistingUser() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);

        UserModel userModel = UserModel.create(USER_ID, NAME, EMAIL, PASSWORD, STATUS, PHONE, roles);

        UserJpa existingUser = new UserJpa();
        existingUser.setId(USER_ID);
        existingUser.setEmail(EMAIL);

        when(userJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(existingUser));

        userAuthPersistenceAdapter.update(userModel);

        verify(userJpaRepository).findByEmail(EMAIL);
        verify(userJpaRepository).save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnUpdate() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);

        UserModel userModel = UserModel.create(USER_ID, NAME, EMAIL, PASSWORD, STATUS, PHONE, roles);

        when(userJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserAuthUserNotFoundException.class, () -> userAuthPersistenceAdapter.update(userModel));
    }

    @Test
    void shouldUpdateUserWithNewName() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        String newName = "andres updated";

        UserModel userModel = UserModel.create(USER_ID, newName, EMAIL, PASSWORD, STATUS, PHONE, roles);

        UserJpa existingUser = new UserJpa();
        existingUser.setId(USER_ID);
        existingUser.setEmail(EMAIL);
        existingUser.setName(NAME);

        when(userJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(existingUser));

        userAuthPersistenceAdapter.update(userModel);

        verify(userJpaRepository).findByEmail(EMAIL);
        verify(userJpaRepository).save(existingUser);
    }
}
