package com.amr.shop.gwy.gateway;

import com.amr.shop.gwy.gateway.exception.GatewayExceptionHdr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebExceptionHandler;

@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder
        .routes()
        .route(
            "users",
            p ->
                p.path("/users/**")
                    .filters(f -> f.rewritePath("/users/(?<segment>.*)", "/${segment}"))
                    .uri("lb://USER-CONTEXT"))
        .route(
            "auth",
            p ->
                p.path("/auth/**")
                    .filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/${segment}"))
                    .uri("lb://AUTH-SERVICE-JAVA"))
        .build();
  }

  @Bean
  public WebExceptionHandler exceptionHandler() {
    return new GatewayExceptionHdr();
  }
}
