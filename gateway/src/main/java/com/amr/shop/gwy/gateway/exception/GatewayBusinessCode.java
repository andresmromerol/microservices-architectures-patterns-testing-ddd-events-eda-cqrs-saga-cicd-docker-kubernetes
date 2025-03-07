package com.amr.shop.gwy.gateway.exception;

public final class GatewayBusinessCode {
  public static final String GATEWAY_ERROR = "GATEWAY_00";
  public static final String VALIDATION_ERROR = "GATEWAY_10";
  public static final String UNAUTHORIZED_ERROR = "GATEWAY_20";
  public static final String TOKEN_EXPIRED = "GATEWAY_30";
  public static final String TOKEN_INVALID = "GATEWAY_40";
  public static final String TOKEN_MISSING = "GATEWAY_50";
  public static final String FORBIDDEN_ACCESS = "GATEWAY_80";

  private GatewayBusinessCode() {}
}
