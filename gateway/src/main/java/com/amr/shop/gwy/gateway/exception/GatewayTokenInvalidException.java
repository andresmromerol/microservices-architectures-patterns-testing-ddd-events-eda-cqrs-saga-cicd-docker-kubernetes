package com.amr.shop.gwy.gateway.exception;

public class GatewayTokenInvalidException extends GatewayException {

  public static final String TOKEN_INVALID = "Token invalid";

  public GatewayTokenInvalidException() {
    super(TOKEN_INVALID);
  }

  public GatewayTokenInvalidException(String message) {
    super(message);
  }

  public GatewayTokenInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
