package com.amr.shop.usr.user_context.user.domain;

public class UserUnknownRoleException extends UserException {

  public static final String UNKNOWN_ROLE = "Unknown role: %s";

  public UserUnknownRoleException(String identifier) {
    super(String.format(UNKNOWN_ROLE, identifier));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
