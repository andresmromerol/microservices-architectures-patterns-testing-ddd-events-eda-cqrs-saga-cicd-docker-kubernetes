package com.amr.shop.athj.auth_service_java.token.domain;

public class TokenException extends RuntimeException {
  public TokenException(String message) {
    super(message);
  }

  public TokenException(String message, Throwable cause) {
    super(message, cause);
  }
}
