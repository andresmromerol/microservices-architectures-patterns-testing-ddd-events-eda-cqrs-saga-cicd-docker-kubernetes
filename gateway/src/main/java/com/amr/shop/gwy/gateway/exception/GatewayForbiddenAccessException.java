package com.amr.shop.gwy.gateway.exception;

public class GatewayForbiddenAccessException extends GatewayException {

  public static final String FORBIDDEN_ACCESS = "Forbidden access";

  public GatewayForbiddenAccessException() {
    super(FORBIDDEN_ACCESS);
  }

  public GatewayForbiddenAccessException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
