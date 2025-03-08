package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthNewPasswordConfirmationInvalidException extends UserAuthException {

  public static final String NEW_PASSWORD_CONFIRMATION_INVALID = "New password does not match";

  public UserAuthNewPasswordConfirmationInvalidException() {
    super(NEW_PASSWORD_CONFIRMATION_INVALID);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
