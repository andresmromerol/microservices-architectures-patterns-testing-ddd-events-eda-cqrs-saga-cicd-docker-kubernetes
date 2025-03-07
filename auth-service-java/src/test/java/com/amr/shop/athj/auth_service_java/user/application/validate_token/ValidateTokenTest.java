package com.amr.shop.athj.auth_service_java.user.application.validate_token;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthTokenExpiredException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ValidateTokenTest {

    private static final String VALID_TOKEN = "valid.jwt.token";
    private static final String VALID_EMAIL = "test@example.com";
    private static final UUID USER_ID = UUID.randomUUID();

    @Mock
    private IQueryBus queryBus;

    @Mock
    private IClaimPort claimPort;

    private ValidateToken validateToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validateToken = new ValidateToken(queryBus, claimPort);
    }

    @Test
    void execute_WithValidToken_ShouldNotThrowException() {
        when(claimPort.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);
        when(queryBus.ask(any(UserSearchByEmailQry.class)))
                .thenReturn(UserSearchByEmailRes.builder()
                        .id(USER_ID)
                        .name("user")
                        .email(VALID_EMAIL)
                        .password("encoded")
                        .phone("3209118911")
                        .roles(new HashSet<>())
                        .permissions(new HashSet<>())
                        .isEmpty(false)
                        .build());
        when(claimPort.extractExpiration(VALID_TOKEN)).thenReturn(new Date(System.currentTimeMillis() + 3600000));

        assertDoesNotThrow(() -> validateToken.execute(VALID_TOKEN));

        verify(claimPort, atLeastOnce()).extractUsername(VALID_TOKEN);
        verify(queryBus).ask(any(UserSearchByEmailQry.class));
        verify(claimPort).extractExpiration(VALID_TOKEN);
    }

    @Test
    void execute_WithNullUsername_ShouldThrowUserAuthException() {
        when(claimPort.extractUsername(VALID_TOKEN)).thenReturn(null);

        UserAuthException exception = assertThrows(UserAuthException.class, () -> validateToken.execute(VALID_TOKEN));

        assertEquals("User not found", exception.getMessage());
        verify(claimPort).extractUsername(VALID_TOKEN);
        verify(queryBus, never()).ask(any());
    }

    @Test
    void execute_WithUserNotFound_ShouldThrowUserAuthException() {
        when(claimPort.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);
        when(queryBus.ask(any(UserSearchByEmailQry.class)))
                .thenReturn(UserSearchByEmailRes.builder().isEmpty(true).build());

        UserAuthException exception = assertThrows(UserAuthException.class, () -> validateToken.execute(VALID_TOKEN));

        assertEquals("User not found with email: " + VALID_EMAIL, exception.getMessage());
        verify(claimPort).extractUsername(VALID_TOKEN);
        verify(queryBus).ask(any(UserSearchByEmailQry.class));
    }

    @Test
    void execute_WithMismatchedUsername_ShouldThrowUserAuthUserNotFoundException() {
        String differentEmail = "mauricio@email.com";

        when(claimPort.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);

        UserSearchByEmailRes userResponse = UserSearchByEmailRes.builder()
                .id(USER_ID)
                .name("mauricio")
                .email(differentEmail)
                .password("encoded")
                .phone("3209118911")
                .roles(new HashSet<>())
                .permissions(new HashSet<>())
                .isEmpty(false)
                .build();
        when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(userResponse);

        UserAuthUserNotFoundException exception =
                assertThrows(UserAuthUserNotFoundException.class, () -> validateToken.execute(VALID_TOKEN));

        assertEquals(String.format("User not found %s", differentEmail), exception.getMessage());

        verify(claimPort, atLeastOnce()).extractUsername(VALID_TOKEN);
        verify(queryBus).ask(any(UserSearchByEmailQry.class));
    }

    @Test
    void execute_WithExpiredToken_ShouldThrowUserAuthTokenExpiredException() {
        when(claimPort.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);

        UserSearchByEmailRes userResponse = UserSearchByEmailRes.builder()
                .id(USER_ID)
                .name("andres")
                .email(VALID_EMAIL)
                .password("encoded")
                .phone("3209118911")
                .roles(new HashSet<>())
                .permissions(new HashSet<>())
                .isEmpty(false)
                .build();
        when(queryBus.ask(any(UserSearchByEmailQry.class))).thenReturn(userResponse);

        Date pastDate = new Date(System.currentTimeMillis() - 3600000);
        when(claimPort.extractExpiration(VALID_TOKEN)).thenReturn(pastDate);

        UserAuthTokenExpiredException exception =
                assertThrows(UserAuthTokenExpiredException.class, () -> validateToken.execute(VALID_TOKEN));

        assertEquals("Token expired", exception.getMessage());

        verify(claimPort, atLeastOnce()).extractUsername(VALID_TOKEN);
        verify(queryBus).ask(any(UserSearchByEmailQry.class));
        verify(claimPort).extractExpiration(VALID_TOKEN);
    }
}
