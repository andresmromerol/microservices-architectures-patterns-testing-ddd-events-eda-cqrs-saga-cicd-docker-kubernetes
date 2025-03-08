package com.amr.shop.gwy.gateway.filter;

import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import java.util.stream.Collectors;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(2)
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private static ServerHttpRequest addInformationHeader(
      ServerWebExchange exchange, String email, String name, String id, String roles) {
    ServerHttpRequest request =
        exchange
            .getRequest()
            .mutate()
            .header("X-User-Email", email)
            .header("X-User-Name", name)
            .header("X-User-Id", id)
            .header("X-User-Roles", roles)
            .build();
    return request;
  }

  private static String extractFromAuthorities(Authentication auth, AuthTitleEnum prefix) {
    return auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .filter(authority -> authority.startsWith(prefix.getValue()))
        .map(authority -> authority.substring(prefix.getLength()))
        .collect(Collectors.joining(","));
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return ReactiveSecurityContextHolder.getContext()
        .flatMap(
            ctx -> {
              Authentication auth = ctx.getAuthentication();
              if (auth != null && auth.isAuthenticated()) {
                return chain.filter(withAuthenticationHeaders(exchange, auth));
              }
              return chain.filter(exchange);
            })
        .switchIfEmpty(chain.filter(exchange));
  }

  private ServerWebExchange withAuthenticationHeaders(
      ServerWebExchange exchange, Authentication auth) {

    if (auth instanceof JwtAuthenticationToken) {
      JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) auth;
      String roles = extractFromAuthorities(auth, AuthTitleEnum.ROLE);
      String name = jwtAuth.getToken().getClaimAsString("name");
      String id = jwtAuth.getToken().getClaimAsString("id");
      String email = jwtAuth.getToken().getSubject();
      return exchange
          .mutate()
          .request(addInformationHeader(exchange, email, name, id, roles))
          .build();
    }

    return exchange;
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
