package com.amr.shop.athj.auth_service_java.token.domain;

public class TokenSaveFailedException extends TokenException {

  public static final String TOKEN_SAVE_FAILED = "Failed to revoke tokens";

  public TokenSaveFailedException() {
    super(TOKEN_SAVE_FAILED);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
