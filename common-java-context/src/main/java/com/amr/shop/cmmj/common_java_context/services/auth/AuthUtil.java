package com.amr.shop.cmmj.common_java_context.services.auth;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;

public class AuthUtil {
  public AuthUtil() {}

  public static String extractBearerToken(String authHeader) {
    if (authHeader == null || !authHeader.startsWith(AuthTitleEnum.BEARER.getValue())) {
      throw new DomainException("Invalid Authorization header");
    }
    return authHeader.substring(AuthNumericEnum.JWT_EXTRACT_INDEX.getValue());
  }
}
