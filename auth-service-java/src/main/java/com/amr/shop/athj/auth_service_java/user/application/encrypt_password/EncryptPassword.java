package com.amr.shop.athj.auth_service_java.user.application.encrypt_password;

import com.amr.shop.athj.auth_service_java.user.domain.ports.IPasswordPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EncryptPassword {

  private final IPasswordPort passwordPort;

  @Autowired
  public EncryptPassword(IPasswordPort passwordPort) {
    this.passwordPort = passwordPort;
  }

  public EncryptPasswordRes execute(String password) {
    log.debug("Encrypting password: {}", password);
    final String encodedPassword = passwordPort.encode(password);
    log.debug("Password encrypted: {}", encodedPassword);
    return response(encodedPassword);
  }

  private EncryptPasswordRes response(String encode) {
    log.debug("Returning encrypted password: {}", encode);
    return new EncryptPasswordRes(encode);
  }
}
