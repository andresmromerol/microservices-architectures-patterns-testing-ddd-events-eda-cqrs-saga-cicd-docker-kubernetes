package com.amr.shop.usr.user_context.user.domain;

public class UserException extends RuntimeException {
  public UserException(String message) {
    super(message);
  }

  public UserException(String message, Throwable cause) {
    super(message, cause);
  }
}
