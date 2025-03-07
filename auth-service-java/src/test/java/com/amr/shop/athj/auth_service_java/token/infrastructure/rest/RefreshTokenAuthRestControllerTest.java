package com.amr.shop.athj.auth_service_java.token.infrastructure.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.exception.AuthExceptionHdr;
import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorQry;
import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorRes;
import com.amr.shop.athj.auth_service_java.user.application.validate_token.ValidateTokenCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthTokenExpiredException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RefreshTokenAuthRestControllerTest {

    private static final String VALID_REFRESH_TOKEN = "valid.refresh.token";
    private static final String VALID_ACCESS_TOKEN = "valid.access.token";
    private static final String VALID_EMAIL = "andres@email.com";

    @Mock
    private IQueryBus queryBus;

    @Mock
    private ICommandBus commandBus;

    @Mock
    private IClaimPort claimPort;

    @InjectMocks
    private RefreshTokenAuthRestController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new AuthExceptionHdr())
                .build();
    }

    @Test
    void refreshToken_WithValidToken_ShouldReturnNewTokens() throws Exception {
        when(claimPort.extractUsername(VALID_REFRESH_TOKEN)).thenReturn(VALID_EMAIL);
        when(queryBus.ask(any(AuthenticatorQry.class)))
                .thenReturn(new AuthenticatorRes(VALID_ACCESS_TOKEN, VALID_REFRESH_TOKEN));

        mockMvc.perform(post("/api/v1/tokens/refresh-token")
                        .header(AuthTitleEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + VALID_REFRESH_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(VALID_ACCESS_TOKEN))
                .andExpect(jsonPath("$.refresh_token").value(VALID_REFRESH_TOKEN));

        verify(claimPort).extractUsername(VALID_REFRESH_TOKEN);
        verify(commandBus).dispatch(any(ValidateTokenCmd.class));
        verify(queryBus).ask(any(AuthenticatorQry.class));
    }

    @Test
    void refreshToken_WithMissingToken_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tokens/refresh-token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(claimPort, never()).extractUsername(anyString());
        verify(commandBus, never()).dispatch(any());
        verify(queryBus, never()).ask(any());
    }

    @Test
    void refreshToken_WithInvalidToken_ShouldReturnUnauthorized() throws Exception {
        String invalidToken = "invalid.token";
        when(claimPort.extractUsername(invalidToken)).thenThrow(new UserAuthException(""));

        mockMvc.perform(post("/api/v1/tokens/refresh-token")
                        .header(AuthTitleEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(claimPort).extractUsername(invalidToken);
        verify(commandBus, never()).dispatch(any());
        verify(queryBus, never()).ask(any());
    }

    @Test
    void refreshToken_WithExpiredToken_ShouldReturnUnauthorized() throws Exception {
        String expiredToken = "expired.token";
        when(claimPort.extractUsername(expiredToken)).thenReturn(VALID_EMAIL);
        doThrow(new UserAuthTokenExpiredException()).when(commandBus).dispatch(any(ValidateTokenCmd.class));

        mockMvc.perform(post("/api/v1/tokens/refresh-token")
                        .header(AuthTitleEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + expiredToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(claimPort).extractUsername(expiredToken);
        verify(commandBus).dispatch(any(ValidateTokenCmd.class));
        verify(queryBus, never()).ask(any());
    }

    @Test
    void refreshToken_WithAuthenticationFailure_ShouldReturnUnauthorized() throws Exception {
        when(claimPort.extractUsername(VALID_REFRESH_TOKEN)).thenReturn(VALID_EMAIL);
        when(queryBus.ask(any(AuthenticatorQry.class))).thenThrow(new UserAuthException(""));

        mockMvc.perform(post("/api/v1/tokens/refresh-token")
                        .header(AuthTitleEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + VALID_REFRESH_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(claimPort).extractUsername(VALID_REFRESH_TOKEN);
        verify(commandBus).dispatch(any(ValidateTokenCmd.class));
        verify(queryBus).ask(any(AuthenticatorQry.class));
    }
}
