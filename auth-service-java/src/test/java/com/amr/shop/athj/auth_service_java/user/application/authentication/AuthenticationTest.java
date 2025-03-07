package com.amr.shop.athj.auth_service_java.user.application.authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

import com.amr.shop.athj.auth_service_java.token.application.token_generate.TokenGenerateQry;
import com.amr.shop.athj.auth_service_java.token.application.token_generate.TokenGenerateRes;
import com.amr.shop.athj.auth_service_java.token.application.token_refresh.TokenRefreshQry;
import com.amr.shop.athj.auth_service_java.token.application.token_refresh.TokenRefreshRes;
import com.amr.shop.athj.auth_service_java.token.application.token_revoke.TokenRevokeCmd;
import com.amr.shop.athj.auth_service_java.token.application.token_save.TokenSaveCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IAuthenticationPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthenticationTest {

    @Mock
    private IQueryBus queryBus;

    @Mock
    private ICommandBus commandBus;

    @Mock
    private IAuthenticationPort authenticationPort;

    private AuthenticationHdr authenticationHdr;
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_EMAIL = "andres@email.com";
    private static final String USER_NAME = "andres";
    private static final String USER_PASSWORD = "123456789";
    private static final String USER_PHONE = "3209118911";
    private static final String SECRET_KEY = "test-secret-key";
    private static final long JWT_EXPIRATION = 3600000L;
    private static final long REFRESH_EXPIRATION = 86400000L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Authentication authentication = new Authentication(queryBus, commandBus, authenticationPort);
        authenticationHdr = new AuthenticationHdr(authentication);
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        Set<RoleEnum> roles = EnumSet.of(RoleEnum.USER);
        Set<PermissionEnum> permissions = EnumSet.of(PermissionEnum.ADMIN_CREATE);

        UserSearchByEmailRes userRes = UserSearchByEmailRes.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .phone(USER_PHONE)
                .roles(roles)
                .permissions(permissions)
                .isEmpty(false)
                .build();

        TokenGenerateRes tokenGenerateRes = new TokenGenerateRes("access-token");
        TokenRefreshRes tokenRefreshRes = new TokenRefreshRes("refresh-token");

        when(authenticationPort.getSecretKey()).thenReturn(SECRET_KEY);
        when(authenticationPort.getJwtExpiration()).thenReturn(JWT_EXPIRATION);
        when(authenticationPort.refreshExpiration()).thenReturn(REFRESH_EXPIRATION);
        when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(userRes);
        when(queryBus.ask(argThat(arg -> arg instanceof TokenGenerateQry))).thenReturn(tokenGenerateRes);
        when(queryBus.ask(argThat(arg -> arg instanceof TokenRefreshQry))).thenReturn(tokenRefreshRes);

        AuthenticatorRes response = authenticationHdr.handle(new AuthenticatorQry(USER_EMAIL));

        assertNotNull(response);
        assertEquals("access-token", response.accessToken());
        assertEquals("refresh-token", response.refreshToken());

        verify(queryBus).ask(eq(new UserSearchByEmailQry(USER_EMAIL)));
        verify(queryBus).ask(argThat(arg -> {
            if (arg instanceof TokenGenerateQry generateQry) {
                BuildTokenDto token = generateQry.token();
                return USER_EMAIL.equals(token.getUsername())
                        && USER_ID.equals(token.getUserId())
                        && USER_NAME.equals(token.getName())
                        && JWT_EXPIRATION == token.getExpiration()
                        && token.getRoles().equals(roles);
            }
            return false;
        }));
        verify(queryBus).ask(argThat(arg -> {
            if (arg instanceof TokenRefreshQry refreshQry) {
                BuildTokenDto token = refreshQry.token();
                return USER_EMAIL.equals(token.getUsername())
                        && USER_ID.equals(token.getUserId())
                        && USER_NAME.equals(token.getName())
                        && REFRESH_EXPIRATION == token.getExpiration()
                        && token.getRoles().equals(roles);
            }
            return false;
        }));
        verify(commandBus).dispatch(eq(new TokenRevokeCmd(USER_ID)));
        verify(commandBus).dispatch(eq(new TokenSaveCmd(USER_ID, "access-token")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UserSearchByEmailRes emptyRes =
                UserSearchByEmailRes.builder().isEmpty(true).build();
        when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(emptyRes);

        assertThrows(
                UserAuthUserNotFoundException.class,
                () -> authenticationHdr.handle(new AuthenticatorQry("andres@email.com")));

        verify(queryBus).ask(eq(new UserSearchByEmailQry("andres@email.com")));
        verifyNoMoreInteractions(queryBus);
        verifyNoInteractions(commandBus);
    }
}
