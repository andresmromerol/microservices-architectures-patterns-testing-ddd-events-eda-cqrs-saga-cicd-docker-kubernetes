package com.amr.shop.gwy.gateway.exception;

import static org.mockito.Mockito.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GatewayExceptionHdrTest {

  private GatewayExceptionHdr exceptionHandler;

  @Mock private ServerWebExchange exchange;

  @Mock private ServerHttpResponse response;

  @Mock private DataBufferFactory dataBufferFactory;

  @Mock private DataBuffer dataBuffer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    exceptionHandler = new GatewayExceptionHdr();
    when(exchange.getResponse()).thenReturn(response);
    when(response.getHeaders()).thenReturn(new org.springframework.http.HttpHeaders());
    when(response.bufferFactory()).thenReturn(dataBufferFactory);
    when(dataBufferFactory.wrap(any(byte[].class))).thenReturn(dataBuffer);
    when(response.writeWith(any(Mono.class))).thenReturn(Mono.empty());
  }

  @Test
  void handleGatewayTokenExpiredException() {
    GatewayTokenExpiredException exception = new GatewayTokenExpiredException();
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleGatewayTokenInvalidException() {
    GatewayTokenInvalidException exception = new GatewayTokenInvalidException();
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleGatewayTokenMissingException() {
    GatewayTokenMissingException exception = new GatewayTokenMissingException();
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleGatewayForbiddenAccessException() {
    GatewayForbiddenAccessException exception = new GatewayForbiddenAccessException();
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.FORBIDDEN);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleExpiredJwtException() {
    ExpiredJwtException exception = mock(ExpiredJwtException.class);
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleSignatureException() {
    SignatureException exception = mock(SignatureException.class);
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleMalformedJwtException() {
    MalformedJwtException exception = mock(MalformedJwtException.class);
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleResponseStatusException() {
    ResponseStatusException exception =
        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.BAD_REQUEST);
    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void handleGenericException() {
    RuntimeException exception = new RuntimeException("Generic error");
    Mono<Void> result = exceptionHandler.handle(exchange, exception);
    verify(response).setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    StepVerifier.create(result).verifyComplete();
  }
}
