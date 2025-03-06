package com.amr.shop.athj.auth_service_java.user_vw.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user_vw.UserViewJpaRepository;
import com.amr.shop.athj.auth_service_java.user_vw.domain.UserView;
import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserViewPersistenceAdapterTest {

    private UserViewPersistenceAdapter userViewPersistenceAdapter;

    @Mock
    private UserViewJpaRepository userViewJpaRepository;

    @Mock
    private UserViewPersistenceMapper userViewPersistenceMapper;

    private static final UUID USER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final String NAME = "user";
    private static final String EMAIL = "user@mail.com";
    private static final String PASSWORD = "123456789";
    private static final String PHONE = "3209118911";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userViewPersistenceAdapter = new UserViewPersistenceAdapter(userViewJpaRepository, userViewPersistenceMapper);
    }

    @Test
    void shouldFindUserByEmailSuccessfully() {
        EmailVo email = new EmailVo(EMAIL);
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);

        Set<PermissionEnum> permissions = new HashSet<>();
        permissions.add(PermissionEnum.ADMIN_READ);

        UserJpa userJpa = UserJpa.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .phone(PHONE)
                .roles(roles)
                .build();

        UserView userView = new UserView(USER_ID, NAME, EMAIL, PASSWORD, PHONE, roles, permissions);

        when(userViewJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(userJpa));
        when(userViewPersistenceMapper.jpaToModel(userJpa)).thenReturn(userView);

        Optional<UserView> result = userViewPersistenceAdapter.search(email);

        assertTrue(result.isPresent());
        assertEquals(userView, result.get());
        verify(userViewJpaRepository).findByEmail(EMAIL);
        verify(userViewPersistenceMapper).jpaToModel(userJpa);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        EmailVo email = new EmailVo(EMAIL);
        when(userViewJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Optional<UserView> result = userViewPersistenceAdapter.search(email);

        assertTrue(result.isEmpty());
        verify(userViewJpaRepository).findByEmail(EMAIL);
    }
}
