package com.amr.shop.gwy.gateway;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.gwy.gateway.exception.GatewayTokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  public Claims extractAllClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (Throwable t) {
      log.error("Error while processing JWT token: {}", t.getMessage());
      throw new GatewayTokenInvalidException();
    }
  }

  private Key getSignInKey() {
    try {
      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      return Keys.hmacShaKeyFor(keyBytes);
    } catch (Throwable t) {
      log.error("Error while generating signature key: {}", t.getMessage());
      throw new GatewayTokenInvalidException();
    }
  }

  public Key getSigningKey() {
    return getSignInKey();
  }

  public Claims parseClaims(String token) {
    return extractAllClaims(token);
  }

  @SuppressWarnings("unchecked")
  private <T extends Enum<T>> Set<String> extractIdsFromResourceAccess(
      String token, String resourceKey, Class<T> enumClass, Function<T, UUID> idExtractor) {
    try {
      Claims claims = extractAllClaims(token);
      Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
      if (resourceAccess != null) {
        List<String> uuids = (List<String>) resourceAccess.get(resourceKey);
        Set<String> result = new HashSet<>();
        if (uuids != null) {
          for (String uuid : uuids) {
            for (T enumValue : enumClass.getEnumConstants()) {
              if (idExtractor.apply(enumValue).toString().equals(uuid)) {
                result.add(uuid);
                break;
              }
            }
          }
        }
        return result;
      }
      return Collections.emptySet();
    } catch (Throwable t) {
      log.error("Error while extracting token IDs: {}", t.getMessage());
      throw new GatewayTokenInvalidException();
    }
  }

  public Set<String> extractRoles(String token) {
    return extractIdsFromResourceAccess(token, "roles", RoleEnum.class, RoleEnum::getId);
  }
}
