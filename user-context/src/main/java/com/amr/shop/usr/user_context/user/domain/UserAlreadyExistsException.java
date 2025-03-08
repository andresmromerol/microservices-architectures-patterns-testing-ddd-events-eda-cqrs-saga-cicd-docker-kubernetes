package com.amr.shop.usr.user_context.user.domain;

public class UserAlreadyExistsException extends UserException {

  public static final String USER_ALREADY_EXISTS = "User already exists: %s";

  public UserAlreadyExistsException(String identifier) {
    super(String.format(USER_ALREADY_EXISTS, identifier));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
