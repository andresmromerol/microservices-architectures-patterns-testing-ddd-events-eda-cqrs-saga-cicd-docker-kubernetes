package com.amr.shop.athj.auth_service_java.user.infrastructure.auth;

import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClaimAdapter implements IClaimPort {
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  @Override
  public String extractUsername(String token) {
    log.info("Extracting username from token");
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Date extractExpiration(String token) {
    log.info("Extracting expiration date from token");
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    log.debug("Extracting claim from token");
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    log.debug("Extracting all claims from token");
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    log.debug("Getting signing key");
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
