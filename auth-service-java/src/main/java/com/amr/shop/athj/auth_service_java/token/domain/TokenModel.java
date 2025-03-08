package com.amr.shop.athj.auth_service_java.token.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.TokenId;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenType;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.shared.abstracts.AggregateRoot;

public class TokenModel extends AggregateRoot<TokenId> {

  private final String token;

  private final TokenType tokenType;

  private final boolean revoked;

  private final boolean expired;

  private final UserId userId;

  public TokenModel(
      TokenId tokenId,
      String token,
      TokenType tokenType,
      boolean revoked,
      boolean expired,
      UserId userId) {
    this.setId(tokenId);
    this.token = token;
    this.tokenType = tokenType;
    this.revoked = revoked;
    this.expired = expired;
    this.userId = userId;
  }

  public static TokenModel create(
      TokenId tokenId,
      String token,
      TokenType tokenType,
      boolean revoked,
      boolean expired,
      UserId userId) {
    return new TokenModel(tokenId, token, tokenType, revoked, expired, userId);
  }

  public String getToken() {
    return token;
  }

  public TokenType getTokenType() {
    return tokenType;
  }

  public boolean isRevoked() {
    return revoked;
  }

  public boolean isExpired() {
    return expired;
  }

  public UserId getUserId() {
    return userId;
  }
}
