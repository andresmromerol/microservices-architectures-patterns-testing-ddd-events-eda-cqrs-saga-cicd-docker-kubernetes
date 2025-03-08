package com.amr.shop.athj.auth_service_java.user.application.authentication_token;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IAuthenticationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthenticationTokenTest {

  @Mock private IAuthenticationPort authenticationPort;

  private AuthenticationToken authenticationToken;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authenticationToken = new AuthenticationToken(authenticationPort);
  }

  @Test
  void shouldAuthenticateValidUser() {
    String email = "andres@email.com";
    String password = "123456789";
    doNothing().when(authenticationPort).authenticationToken(email, password);
    authenticationToken.execute(email, password);
    verify(authenticationPort).authenticationToken(email, password);
  }

  @Test
  void shouldThrowExceptionWhenAuthenticationFails() {
    String email = "andres@email.com";
    String password = "123456789";
    doThrow(new RuntimeException("Authentication failed"))
        .when(authenticationPort)
        .authenticationToken(email, password);
    assertThrows(
        UserAuthAuthenticationFailedException.class,
        () -> authenticationToken.execute(email, password));
  }
}
