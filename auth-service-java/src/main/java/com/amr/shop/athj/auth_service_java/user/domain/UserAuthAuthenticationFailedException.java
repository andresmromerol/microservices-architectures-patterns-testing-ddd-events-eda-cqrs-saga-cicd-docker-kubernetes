package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthAuthenticationFailedException extends UserAuthException {

  public static final String FAILD_TO_AUTHENTICATE = "Failed to authenticate with email: %s";

  public UserAuthAuthenticationFailedException(String email) {
    super(String.format(FAILD_TO_AUTHENTICATE, email));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
