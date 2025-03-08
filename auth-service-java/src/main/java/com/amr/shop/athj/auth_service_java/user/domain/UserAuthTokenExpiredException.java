package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthTokenExpiredException extends UserAuthException {

  public static final String TOKEN_EXPIRED = "Token expired";

  public UserAuthTokenExpiredException() {
    super(TOKEN_EXPIRED);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
