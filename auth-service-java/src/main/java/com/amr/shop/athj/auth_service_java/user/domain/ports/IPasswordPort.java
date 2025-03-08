package com.amr.shop.athj.auth_service_java.user.domain.ports;

public interface IPasswordPort {
  String encode(String password);

  boolean matches(String rawPassword, String encodedPassword);
}
