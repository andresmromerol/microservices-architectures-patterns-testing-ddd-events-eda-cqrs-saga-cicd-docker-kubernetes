package com.amr.shop.athj.auth_service_java.user.application.change_password;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordQry;
import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordRes;
import com.amr.shop.athj.auth_service_java.user.application.user_update.UserUpdateCmd;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IPasswordPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ChangePasswordTest {

    @Mock
    private IQueryBus queryBus;

    @Mock
    private ICommandBus commandBus;

    @Mock
    private IPasswordPort passwordPort;

    private ChangePassword changePassword;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        changePassword = new ChangePassword(queryBus, commandBus, passwordPort);
    }

    @Test
    void shouldChangePassword() {
        UUID userId = UUID.randomUUID();
        String name = "andres";
        String email = "andres@email.com";
        String currentPassword = "currentPassword";
        String encodedCurrentPassword = "encodedCurrentPassword";
        String newPassword = "newPassword";
        String confirmationPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";
        String phone = "3209118911";
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        Set<PermissionEnum> permissions = new HashSet<>();
        permissions.add(PermissionEnum.ADMIN_CREATE);

        UserSearchByEmailRes userRes = UserSearchByEmailRes.builder()
                .id(userId)
                .name(name)
                .email(email)
                .password(encodedCurrentPassword)
                .phone(phone)
                .roles(roles)
                .permissions(permissions)
                .isEmpty(false)
                .build();

        when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(userRes);
        when(passwordPort.matches(currentPassword, encodedCurrentPassword)).thenReturn(true);
        when(queryBus.ask(any(EncryptPasswordQry.class))).thenReturn(new EncryptPasswordRes(encodedNewPassword));

        changePassword.execute(email, currentPassword, newPassword, confirmationPassword);

        verify(queryBus).ask(any(UserSearchByEmailQry.class));
        verify(passwordPort).matches(currentPassword, encodedCurrentPassword);
        verify(queryBus).ask(any(EncryptPasswordQry.class));
        verify(commandBus).dispatch(any(UserUpdateCmd.class));
    }
}
