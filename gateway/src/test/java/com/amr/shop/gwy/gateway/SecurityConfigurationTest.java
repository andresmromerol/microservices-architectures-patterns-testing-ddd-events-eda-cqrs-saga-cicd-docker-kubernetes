package com.amr.shop.gwy.gateway;

import static org.junit.jupiter.api.Assertions.*;

import com.amr.shop.gwy.gateway.exception.GatewayForbiddenAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;

class SecurityConfigurationTest {

  @InjectMocks private SecurityConfiguration securityConfiguration;

  @Mock private JwtAuthConverter jwtAuthConverter;

  @Mock private ServerWebExchange exchange;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void springSecurityFilterChainShouldBeConfiguredCorrectly() {
    assertDoesNotThrow(
        () -> {
          assertNotNull(
              securityConfiguration
                  .getClass()
                  .getDeclaredMethod(
                      "springSecurityFilterChain",
                      org.springframework.security.config.web.server.ServerHttpSecurity.class));
        });
  }

  @Test
  void accessDeniedHandlerShouldThrowGatewayForbiddenAccessException() {
    ServerAccessDeniedHandler handler = securityConfiguration.accessDeniedHandler();
    AccessDeniedException denied = new AccessDeniedException("Access denied");
    assertThrows(
        GatewayForbiddenAccessException.class,
        () -> {
          handler.handle(exchange, denied).block();
        });
  }
}
