package com.amr.shop.cmmj.common_java_context.shared.exception;

public class DomainException extends RuntimeException {

  public DomainException(String message) {

    super(message);
  }

  public DomainException(String message, Throwable cause) {

    super(message, cause);
  }
}
