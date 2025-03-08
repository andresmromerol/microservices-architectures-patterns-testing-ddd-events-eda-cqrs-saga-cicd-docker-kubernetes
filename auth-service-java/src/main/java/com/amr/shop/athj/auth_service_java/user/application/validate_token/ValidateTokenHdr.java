package com.amr.shop.athj.auth_service_java.user.application.validate_token;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenHdr implements ICommandHandler<ValidateTokenCmd> {
  private final ValidateToken validateToken;

  @Autowired
  public ValidateTokenHdr(ValidateToken validateToken) {
    this.validateToken = validateToken;
  }

  @Override
  public void handle(ValidateTokenCmd c) {
    validateToken.execute(c.token());
  }
}
