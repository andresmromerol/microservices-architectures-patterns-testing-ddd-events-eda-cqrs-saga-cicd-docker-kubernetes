package com.amr.shop.usr.user_context.user.domain;

public class UserNotFoundException extends UserException {

  public static final String USER_NOT_FOUND = "User not found: %s";

  public UserNotFoundException(String identifier) {
    super(String.format(USER_NOT_FOUND, identifier));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
