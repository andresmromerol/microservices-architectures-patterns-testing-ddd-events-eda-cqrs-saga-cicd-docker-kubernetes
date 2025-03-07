package com.amr.shop.gwy.gateway.exception;

public class GatewayTokenMissingException extends GatewayException {

  public static final String TOKEN_MISSING = "Token missing";

  public GatewayTokenMissingException() {
    super(TOKEN_MISSING);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
