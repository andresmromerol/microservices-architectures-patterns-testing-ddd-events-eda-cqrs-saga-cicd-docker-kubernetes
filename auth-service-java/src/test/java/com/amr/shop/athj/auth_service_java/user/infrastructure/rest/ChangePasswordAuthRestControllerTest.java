package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.athj.auth_service_java.user.application.change_password.ChangePasswordCmd;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.ChangePasswordRequest;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ChangePasswordAuthRestControllerTest {

  @Mock private ICommandBus commandBus;

  @Mock private IQueryBus queryBus;

  @Mock private IClaimPort claimPort;

  @Mock private HttpServletRequest httpRequest;

  private ChangePasswordAuthRestController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new ChangePasswordAuthRestController(queryBus, commandBus, claimPort);
  }

  @Test
  void shouldChangePassword() {
    String email = "andres@email.com";
    String currentPassword = "currentPassword";
    String newPassword = "newPassword";
    String confirmationPassword = "newPassword";
    String bearerToken = "Bearer token";
    String token = "token";
    ChangePasswordRequest request =
        ChangePasswordRequest.builder()
            .currentPassword(currentPassword)
            .newPassword(newPassword)
            .confirmationPassword(confirmationPassword)
            .build();
    when(httpRequest.getHeader(AuthTitleEnum.AUTHORIZATION_HEADER.getValue()))
        .thenReturn(bearerToken);
    when(claimPort.extractUsername(token)).thenReturn(email);
    ResponseEntity<?> response = controller.changePassword(request, httpRequest);
    verify(claimPort).extractUsername(eq(token));
    verify(commandBus).dispatch(any(AuthenticationTokenCmd.class));
    verify(commandBus).dispatch(any(ChangePasswordCmd.class));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
