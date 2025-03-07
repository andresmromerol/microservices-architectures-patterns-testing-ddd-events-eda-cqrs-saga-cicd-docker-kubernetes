package com.amr.shop.gwy.gateway.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class GatewayExceptionTest {

  @Test
  void testGatewayTokenExpiredException() {

    GatewayTokenExpiredException exception = new GatewayTokenExpiredException();
    assertEquals(GatewayTokenExpiredException.TOKEN_EXPIRED, exception.getMessage());
  }

  @Test
  void testGatewayTokenInvalidException() {
    GatewayTokenInvalidException exception = new GatewayTokenInvalidException();
    assertEquals(GatewayTokenInvalidException.TOKEN_INVALID, exception.getMessage());
  }

  @Test
  void testGatewayTokenMissingException() {
    GatewayTokenMissingException exception = new GatewayTokenMissingException();
    assertEquals(GatewayTokenMissingException.TOKEN_MISSING, exception.getMessage());
  }

  @Test
  void testGatewayForbiddenAccessException() {
    GatewayForbiddenAccessException exception = new GatewayForbiddenAccessException();
    assertEquals(GatewayForbiddenAccessException.FORBIDDEN_ACCESS, exception.getMessage());
  }

  @Test
  void testExceptionDTO() {
    int code = 401;
    String businessCode = GatewayBusinessCode.TOKEN_EXPIRED;
    String message = "test";
    ExceptionDTO dto = new ExceptionDTO(code, businessCode, Collections.singletonList(message));
    assertEquals(code, dto.getCode());
    assertEquals(businessCode, dto.getBusinessCode());
    assertNotNull(dto.getMessages());
    assertEquals(1, dto.getMessages().size());
    assertEquals(message, dto.getMessages().get(0));
  }
}
