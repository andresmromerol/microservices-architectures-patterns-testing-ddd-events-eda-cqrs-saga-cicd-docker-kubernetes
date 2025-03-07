package com.amr.shop.gwy.gateway.exception;

import static com.amr.shop.gwy.gateway.exception.GatewayBusinessCode.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayExceptionHdr implements ErrorWebExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHdr.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
    DataBuffer dataBuffer;
    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    if (ex instanceof GatewayTokenExpiredException) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(HttpStatus.UNAUTHORIZED, TOKEN_EXPIRED, ex.getMessage()));
    } else if (ex instanceof GatewayTokenInvalidException
        || ex instanceof SignatureException
        || ex instanceof MalformedJwtException) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(HttpStatus.UNAUTHORIZED, TOKEN_INVALID, ex.getMessage()));
    } else if (ex instanceof GatewayTokenMissingException) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(HttpStatus.UNAUTHORIZED, TOKEN_MISSING, ex.getMessage()));
    } else if (ex instanceof GatewayForbiddenAccessException) {
      exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(HttpStatus.FORBIDDEN, FORBIDDEN_ACCESS, ex.getMessage()));
    } else if (ex instanceof ExpiredJwtException) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(HttpStatus.UNAUTHORIZED, TOKEN_EXPIRED, "Token expired"));
    } else if (ex instanceof ResponseStatusException) {
      ResponseStatusException responseStatusException = (ResponseStatusException) ex;
      exchange.getResponse().setStatusCode(responseStatusException.getStatusCode());
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(
                  HttpStatus.valueOf(responseStatusException.getStatusCode().value()),
                  GATEWAY_ERROR,
                  responseStatusException.getMessage()));
    } else {
      log.error("Unhandled error: ", ex);
      exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      dataBuffer =
          getDataBuffer(
              bufferFactory,
              buildExceptionDTO(
                  HttpStatus.INTERNAL_SERVER_ERROR, GATEWAY_ERROR, "Internal server error"));
    }

    return exchange.getResponse().writeWith(Mono.just(dataBuffer));
  }

  private DataBuffer getDataBuffer(DataBufferFactory bufferFactory, ExceptionDTO exceptionDTO) {
    try {
      byte[] bytes = objectMapper.writeValueAsBytes(exceptionDTO);
      return bufferFactory.wrap(bytes);
    } catch (JsonProcessingException e) {
      log.error("Exception serializing ExceptionDTO: ", e);
      return bufferFactory.wrap("Internal server error".getBytes());
    }
  }

  private ExceptionDTO buildExceptionDTO(HttpStatus status, String businessCode, String message) {
    return buildExceptionDTO(status, businessCode, Collections.singletonList(message));
  }

  private ExceptionDTO buildExceptionDTO(
      HttpStatus status, String businessCode, List<String> messages) {
    return new ExceptionDTO(status.value(), businessCode, messages);
  }
}
