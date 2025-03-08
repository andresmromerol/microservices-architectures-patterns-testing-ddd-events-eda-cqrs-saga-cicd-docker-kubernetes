package com.amr.shop.cmmj.common_java_context.services.auth;

import com.amr.shop.cmmj.common_java_context.shared.abstracts.Id;
import java.util.UUID;

public class TokenId extends Id<UUID> {
  public TokenId(UUID value) {
    super(value);
  }
}
