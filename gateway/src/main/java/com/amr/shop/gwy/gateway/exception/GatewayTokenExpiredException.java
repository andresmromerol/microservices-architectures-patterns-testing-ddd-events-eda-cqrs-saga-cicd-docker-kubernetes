package com.amr.shop.gwy.gateway.exception;

public class GatewayTokenExpiredException extends GatewayException {

  public static final String TOKEN_EXPIRED = "Token expired";

  public GatewayTokenExpiredException() {
    super(TOKEN_EXPIRED);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
