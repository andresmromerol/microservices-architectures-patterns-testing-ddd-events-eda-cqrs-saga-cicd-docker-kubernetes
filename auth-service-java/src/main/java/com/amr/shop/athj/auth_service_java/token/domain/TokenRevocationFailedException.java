package com.amr.shop.athj.auth_service_java.token.domain;

import java.util.UUID;

public class TokenRevocationFailedException extends TokenException {

  public static final String TOKEN_REVOCATION_FAILED = "Failed to revoke tokens for user id: %s";

  public TokenRevocationFailedException(UUID user) {
    super(String.format(TOKEN_REVOCATION_FAILED, user));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
