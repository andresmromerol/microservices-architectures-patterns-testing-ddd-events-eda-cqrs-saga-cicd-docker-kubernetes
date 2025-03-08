package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthUserNotFoundException extends UserAuthException {

  public static final String EMAIL_ALREADY_EXISTS = "User not found %s";

  public UserAuthUserNotFoundException(String email) {
    super(String.format(EMAIL_ALREADY_EXISTS, email));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
