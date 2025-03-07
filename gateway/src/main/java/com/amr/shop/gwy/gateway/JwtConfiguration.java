package com.amr.shop.gwy.gateway;

import com.amr.shop.gwy.gateway.exception.GatewayTokenExpiredException;
import com.amr.shop.gwy.gateway.exception.GatewayTokenInvalidException;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtConfiguration {

  private static final Logger log = LoggerFactory.getLogger(JwtConfiguration.class);

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  private static SecretKeySpec getSecretKey(byte[] decodedKey) {
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, MacAlgorithm.HS256.getName());
  }

  private static void ensureTokenIsValid(NimbusReactiveJwtDecoder jwtDecoder) {
    jwtDecoder.setJwtValidator(
        jwt -> {
          var result = OAuth2TokenValidatorResult.success();
          validateToken(jwt);
          return result;
        });
  }

  private static void validateToken(Jwt jwt) {
    if (jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(java.time.Instant.now())) {
      log.error("The token is expired");
      throw new GatewayTokenExpiredException();
    }
    if (jwt.getSubject() == null) {
      log.error("The token does not have a valid subject");
      throw new GatewayTokenInvalidException();
    }
  }

  @Bean
  public ReactiveJwtDecoder jwtDecoder() {
    var decodedKey = Base64.getDecoder().decode(secretKey);
    var key = getSecretKey(decodedKey);
    var jwtDecoder =
        NimbusReactiveJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    ensureTokenIsValid(jwtDecoder);
    return jwtDecoder;
  }
}
