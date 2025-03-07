package com.amr.shop.gwy.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amr.shop.gwy.gateway.exception.GatewayTokenExpiredException;
import com.amr.shop.gwy.gateway.exception.GatewayTokenInvalidException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.util.ReflectionTestUtils;

class JwtConfigurationTest {

  @InjectMocks private JwtConfiguration jwtConfiguration;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(
        jwtConfiguration,
        "secretKey",
        "6F7564526E4A586C6F336437754C35766B594E5138394650425378586C414F69");
  }

  @Test
  void jwtDecoderShouldCreateValidDecoder() {
    ReactiveJwtDecoder decoder = jwtConfiguration.jwtDecoder();
    assertNotNull(decoder);
    assertTrue(decoder instanceof NimbusReactiveJwtDecoder);
  }

  @Test
  void ensureTokenIsValidShouldThrowExceptionWhenTokenIsExpired() {
    Jwt jwt = mock(Jwt.class);
    when(jwt.getExpiresAt()).thenReturn(Instant.now().minusSeconds(3600));
    assertThrows(
        GatewayTokenExpiredException.class,
        () -> {
          ReflectionTestUtils.invokeMethod(jwtConfiguration, "validateToken", jwt);
        });
  }

  @Test
  void ensureTokenIsValidShouldThrowExceptionWhenSubjectIsMissing() {
    Jwt jwt = mock(Jwt.class);
    when(jwt.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));
    when(jwt.getSubject()).thenReturn(null);
    assertThrows(
        GatewayTokenInvalidException.class,
        () -> {
          ReflectionTestUtils.invokeMethod(jwtConfiguration, "validateToken", jwt);
        });
  }

  @Test
  void ensureTokenIsValidShouldPassValidToken() {
    Jwt jwt = mock(Jwt.class);
    when(jwt.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));
    when(jwt.getSubject()).thenReturn("valid-subject");
    assertDoesNotThrow(
        () -> {
          ReflectionTestUtils.invokeMethod(jwtConfiguration, "validateToken", jwt);
        });
  }
}
