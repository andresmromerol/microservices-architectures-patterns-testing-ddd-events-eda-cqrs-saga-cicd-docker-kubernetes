package com.amr.shop.cmmj.common_java_context.services.auth;

public enum CQBusEnum {
  AUTH_SERVICE_JAVA("com.amr.shop.athj.auth_service_java.*"),
  USER_CONTEXT("com.amr.shop.usr.user_context.*");

  private final String value;

  CQBusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
