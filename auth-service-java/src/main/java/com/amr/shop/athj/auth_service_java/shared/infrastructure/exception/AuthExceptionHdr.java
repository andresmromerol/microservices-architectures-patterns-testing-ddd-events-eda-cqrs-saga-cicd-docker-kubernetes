package com.amr.shop.athj.auth_service_java.shared.infrastructure.exception;

import static com.amr.shop.athj.auth_service_java.shared.infrastructure.exception.AuthBusinessCode.*;

import com.amr.shop.athj.auth_service_java.token.domain.TokenRevocationFailedException;
import com.amr.shop.athj.auth_service_java.token.domain.TokenSaveFailedException;
import com.amr.shop.athj.auth_service_java.token.domain.ValidTokenNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.*;
import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHdr {

  @ExceptionHandler(UserAuthException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleUserAuthException(UserAuthException exception) {
    log.error("UserAuthException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, AUTH_ERROR, exception.getMessage());
  }

  @ExceptionHandler(UserAuthEmailAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ExceptionDTO handleEmailAlreadyExistsException(
      UserAuthEmailAlreadyExistsException exception) {
    log.error("EmailAlreadyExistsException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS, exception.getMessage());
  }

  @ExceptionHandler(UserAuthAuthenticationFailedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionDTO handlerUserAuthAuthenticationFailed(
      UserAuthAuthenticationFailedException exception) {
    log.error("UserAuthAuthenticationFailedException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.UNAUTHORIZED, AUTH_UNAUTHORIZED, exception.getMessage());
  }

  @ExceptionHandler(UserAuthUserNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionDTO handlerUserAuthUserNotFoundException(
      UserAuthUserNotFoundException exception) {
    log.error("UserAuthUserNotFoundException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.UNAUTHORIZED, AUTH_NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(TokenRevocationFailedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionDTO handlerTokenRevocationFailed(TokenRevocationFailedException exception) {
    log.error("TokenRevocationFailedException occurred: {}", exception.getMessage());
    return buildExceptionDTO(
        HttpStatus.INTERNAL_SERVER_ERROR, AUTH_TOKEN_REVOCATION_FAILED, exception.getMessage());
  }

  @ExceptionHandler(TokenSaveFailedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionDTO handlerTokenSaveFailed(TokenSaveFailedException exception) {
    log.error("TokenSaveFailedException occurred: {}", exception.getMessage());
    return buildExceptionDTO(
        HttpStatus.INTERNAL_SERVER_ERROR, AUTH_TOKEN_SAVE_FAILED, exception.getMessage());
  }

  @ExceptionHandler(ValidTokenNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionDTO handlerValidTokensNotFound(ValidTokenNotFoundException exception) {
    log.error("ValidTokensNotFoundException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.UNAUTHORIZED, AUTH_TOKEN_NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(UserAuthPasswordConfirmationInvalidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handlerValidTokensNotFound(
      UserAuthPasswordConfirmationInvalidException exception) {
    log.error("UserAuthPasswordConfirmationInvalidException occurred: {}", exception.getMessage());
    return buildExceptionDTO(
        HttpStatus.BAD_REQUEST, AUTH_PASSWORD_CONFIRMATION_INVALID, exception.getMessage());
  }

  @ExceptionHandler(UserAuthNewPasswordConfirmationInvalidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handlerValidTokensNotFound(
      UserAuthNewPasswordConfirmationInvalidException exception) {
    log.error(
        "UserAuthNewPasswordConfirmationInvalidException occurred: {}", exception.getMessage());
    return buildExceptionDTO(
        HttpStatus.BAD_REQUEST, AUTH_NEW_PASSWORD_CONFIRMATION_INVALID, exception.getMessage());
  }

  @ExceptionHandler(UserAuthTokenExpiredException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionDTO handlerValidTokensNotFound(UserAuthTokenExpiredException exception) {
    log.error("UserAuthTokenExpiredException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.UNAUTHORIZED, AUTH_TOKEN_EXPIRED, exception.getMessage());
  }

  @ExceptionHandler(DomainException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleDomainException(DomainException exception) {
    log.error("DomainException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors =
        Stream.concat(
                ex.getBindingResult().getFieldErrors().stream()
                    .map(
                        error ->
                            String.format("%s: %s", error.getField(), error.getDefaultMessage())),
                ex.getBindingResult().getGlobalErrors().stream()
                    .map(
                        error ->
                            String.format(
                                "%s: %s", error.getObjectName(), error.getDefaultMessage())))
            .collect(Collectors.toList());

    log.error("Validation errors occurred: {}", errors);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleBindException(BindException ex) {
    List<String> errors =
        Stream.concat(
                ex.getBindingResult().getFieldErrors().stream()
                    .map(
                        error ->
                            String.format("%s: %s", error.getField(), error.getDefaultMessage())),
                ex.getBindingResult().getGlobalErrors().stream()
                    .map(
                        error ->
                            String.format(
                                "%s: %s", error.getObjectName(), error.getDefaultMessage())))
            .collect(Collectors.toList());

    log.error("Bind errors occurred: {}", errors);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleConstraintViolationException(ConstraintViolationException ex) {
    List<String> errors =
        ex.getConstraintViolations().stream()
            .map(
                violation ->
                    String.format("%s: %s", getPropertyName(violation), violation.getMessage()))
            .collect(Collectors.toList());

    log.error("Constraint violation errors occurred: {}", errors);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    String error = "Invalid JSON format: Unable to read message";
    log.error("Message not readable: {}", ex.getMessage());
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, error);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String error =
        String.format(
            "Parameter '%s' must be of type %s",
            ex.getName(),
            Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName).orElse("unknown"));

    log.error("Type mismatch: {}", error);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, error);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex) {
    String error = String.format("Parameter '%s' is required", ex.getParameterName());
    log.error("Missing parameter: {}", error);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, error);
  }

  private String getPropertyName(ConstraintViolation<?> violation) {
    return Optional.ofNullable(violation.getPropertyPath())
        .map(
            path -> {
              String[] parts = path.toString().split("\\.");
              return parts[parts.length - 1];
            })
        .orElse("unknown");
  }

  private ExceptionDTO buildExceptionDTO(HttpStatus status, String businessCode, String message) {
    return buildExceptionDTO(status, businessCode, Collections.singletonList(message));
  }

  private ExceptionDTO buildExceptionDTO(
      HttpStatus status, String businessCode, List<String> messages) {
    return ExceptionDTO.builder()
        .code(status.value())
        .businessCode(businessCode)
        .messages(messages)
        .build();
  }
}
