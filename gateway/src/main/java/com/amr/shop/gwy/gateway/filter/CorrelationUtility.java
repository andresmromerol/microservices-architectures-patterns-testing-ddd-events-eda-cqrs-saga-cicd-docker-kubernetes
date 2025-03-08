package com.amr.shop.gwy.gateway.filter;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class CorrelationUtility {

  public static final String CORRELATION_ID = "X-Correlation-Id";

  public String getCorrelationId(HttpHeaders requestHeaders) {
    if (requestHeaders.get(CORRELATION_ID) != null) {
      List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
      return requestHeaderList.stream().findFirst().get();
    } else {
      return null;
    }
  }

  public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
    return exchange
        .mutate()
        .request(exchange.getRequest().mutate().header(name, value).build())
        .build();
  }

  public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
  }
}
