package com.amr.shop.athj.auth_service_java.user.infrastructure.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthenticationAdapterTest {

  @Mock private AuthenticationManager authenticationManager;

  private AuthenticationAdapter authenticationAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authenticationAdapter = new AuthenticationAdapter(authenticationManager);
  }

  @Test
  void shouldAuthenticateValidUser() {
    String email = "andres@email.com";
    String password = "123456789";
    Authentication mockAuth = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(mockAuth);
    assertDoesNotThrow(() -> authenticationAdapter.authenticationToken(email, password));
    verify(authenticationManager)
        .authenticate(
            argThat(
                auth ->
                    auth.getPrincipal().equals(email) && auth.getCredentials().equals(password)));
  }

  @Test
  void shouldThrowExceptionWhenAuthenticationFails() {
    String email = "andres@email.com";
    String password = "123456789";
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Invalid credentials"));
    assertThrows(
        UserAuthAuthenticationFailedException.class,
        () -> authenticationAdapter.authenticationToken(email, password));
    verify(authenticationManager)
        .authenticate(
            argThat(
                auth ->
                    auth.getPrincipal().equals(email) && auth.getCredentials().equals(password)));
  }
}
