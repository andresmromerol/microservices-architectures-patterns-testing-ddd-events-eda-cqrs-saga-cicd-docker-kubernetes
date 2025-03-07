package com.amr.shop.gwy.gateway;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.gwy.gateway.exception.GatewayForbiddenAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

  private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

  @Autowired private JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges
                    .pathMatchers("/actuator/**", "/auth/**")
                    .permitAll()
                    .pathMatchers("/users/**")
                    .hasAnyRole(RoleEnum.ADMIN.getValue())
                    .anyExchange()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 ->
                oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                    .accessDeniedHandler(accessDeniedHandler()));
    return http.build();
  }

  @Bean
  public ServerAccessDeniedHandler accessDeniedHandler() {
    return (exchange, denied) -> {
      log.error("Access to the requested resource has been denied");
      throw new GatewayForbiddenAccessException();
    };
  }
}
