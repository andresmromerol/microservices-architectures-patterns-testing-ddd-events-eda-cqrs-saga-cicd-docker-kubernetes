package com.amr.shop.gwy.gateway;

import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.gwy.gateway.exception.GatewayTokenExpiredException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthConverter.class);

  @Autowired private JwtUtil jwtUtil;

  private static List<SimpleGrantedAuthority> getExtractRoles(Set<String> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(AuthTitleEnum.ROLE.getValue() + role))
        .collect(Collectors.toList());
  }

  private static Mono<AbstractAuthenticationToken> ensureTokenNotExpired(Jwt jwt) {
    if (jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(java.time.Instant.now())) {
      log.error("The token for user {} has expired", jwt.getSubject());
      throw new GatewayTokenExpiredException();
    }
    return null;
  }

  @Override
  public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
    Mono<AbstractAuthenticationToken> empty = ensureTokenNotExpired(jwt);
    if (empty != null) return empty;
    try {
      Set<String> roles = getRoles(jwt);
      List<SimpleGrantedAuthority> authorities = getExtractRoles(roles);
      return Mono.just(new JwtAuthenticationToken(jwt, authorities, jwt.getSubject()));
    } catch (Throwable t) {
      log.error("An error occurred while processing the JWT token: {}", t.getMessage());
      return Mono.empty();
    }
  }

  private Set<String> getRoles(Jwt jwt) {
    return jwtUtil.extractRoles(jwt.getTokenValue());
  }
}
