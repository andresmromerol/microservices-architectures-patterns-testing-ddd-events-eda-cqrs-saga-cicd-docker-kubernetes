package com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.amr.shop.athj.auth_service_java.user_vw.domain.IUserViewPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.domain.UserView;
import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.vo.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserSearchByEmailTest {

    @Mock
    private IUserViewPersistencePort userViewPersistencePort;

    private UserSearchByEmail userSearchByEmail;

    private static final String EMAIL = "user@email.com";
    private static final String PASSWORD = "123456789";
    private static final String NAME = "user";
    private static final String PHONE = "3209118911";
    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSearchByEmail = new UserSearchByEmail(userViewPersistencePort);
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        Set<PermissionEnum> permissions = new HashSet<>();
        permissions.add(PermissionEnum.ADMIN_CREATE);

        UserView userView = new UserView(ID, NAME, EMAIL, PASSWORD, PHONE, roles, permissions);

        when(userViewPersistencePort.search(any(EmailVo.class))).thenReturn(Optional.of(userView));

        UserSearchByEmailRes result = userSearchByEmail.execute(EMAIL);

        assertFalse(result.isEmpty());
        assertEquals(ID, result.id());
        assertEquals(NAME, result.name());
        assertEquals(EMAIL, result.email());
        assertEquals(PASSWORD, result.password());
        assertEquals(PHONE, result.phone());
        assertEquals(roles, result.roles());
        assertEquals(permissions, result.permissions());
    }

    @Test
    void shouldReturnEmptyResponseWhenEmailDoesNotExist() {
        when(userViewPersistencePort.search(any(EmailVo.class))).thenReturn(Optional.empty());

        UserSearchByEmailRes result = userSearchByEmail.execute(EMAIL);

        assertTrue(result.isEmpty());
        assertNull(result.id());
        assertNull(result.name());
        assertNull(result.email());
        assertNull(result.password());
        assertNull(result.phone());
        assertNull(result.roles());
        assertNull(result.permissions());
    }
}
