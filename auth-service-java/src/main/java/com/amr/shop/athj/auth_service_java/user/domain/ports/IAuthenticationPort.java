package com.amr.shop.athj.auth_service_java.user.domain.ports;

public interface IAuthenticationPort {
  void authenticationToken(String email, String password);

  String getSecretKey();

  long getJwtExpiration();

  long refreshExpiration();
}
