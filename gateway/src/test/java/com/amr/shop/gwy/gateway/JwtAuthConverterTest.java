package com.amr.shop.gwy.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.gwy.gateway.exception.GatewayTokenExpiredException;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class JwtAuthConverterTest {

  @Mock private JwtUtil jwtUtil;

  @InjectMocks private JwtAuthConverter jwtAuthConverter;

  @Mock private Jwt jwt;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void convertShouldReturnTokenWhenJwtIsValid() {
    when(jwt.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));
    when(jwt.getSubject()).thenReturn("user");
    when(jwt.getTokenValue()).thenReturn("valid-token");
    when(jwtUtil.extractRoles(anyString())).thenReturn(Set.of("USER"));
    Mono<AbstractAuthenticationToken> result = jwtAuthConverter.convert(jwt);
    StepVerifier.create(result)
        .expectNextMatches(
            token ->
                token.getName().equals("user")
                    && token.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER")))
        .verifyComplete();
  }

  @Test
  void convertShouldThrowExceptionWhenTokenIsExpired() {
    when(jwt.getExpiresAt()).thenReturn(Instant.now().minusSeconds(3600));
    when(jwt.getSubject()).thenReturn("user");
    assertThrows(GatewayTokenExpiredException.class, () -> jwtAuthConverter.convert(jwt));
  }

  @Test
  void convertShouldReturnEmptyWhenExceptionOccurs() {
    when(jwt.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));
    when(jwt.getSubject()).thenReturn("user");
    when(jwt.getTokenValue()).thenReturn("valid-token");
    when(jwtUtil.extractRoles(anyString())).thenThrow(new RuntimeException("Test exception"));
    Mono<AbstractAuthenticationToken> result = jwtAuthConverter.convert(jwt);
    StepVerifier.create(result).verifyComplete();
  }
}
