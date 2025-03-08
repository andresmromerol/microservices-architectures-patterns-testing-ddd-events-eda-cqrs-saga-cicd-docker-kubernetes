package com.amr.shop.gwy.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class CorrelationFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(CorrelationFilter.class);

  @Autowired CorrelationUtility correlationUtility;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
    if (isCorrelationIdPresent(requestHeaders)) {
      logger.debug(
          "shop-correlation-id found in RequestTraceFilter : {}",
          correlationUtility.getCorrelationId(requestHeaders));
    } else {
      String correlationID = generateCorrelationId();
      exchange = correlationUtility.setCorrelationId(exchange, correlationID);
      logger.debug("shop-correlation-id generated in RequestTraceFilter : {}", correlationID);
    }
    return chain.filter(exchange);
  }

  private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
    if (correlationUtility.getCorrelationId(requestHeaders) != null) {
      return true;
    } else {
      return false;
    }
  }

  private String generateCorrelationId() {
    return java.util.UUID.randomUUID().toString();
  }
}
