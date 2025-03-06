package com.amr.shop.athj.auth_service_java.user.domain.ports;

public interface IAuthenticationPort {
    void authenticationToken(String email, String password);
}
