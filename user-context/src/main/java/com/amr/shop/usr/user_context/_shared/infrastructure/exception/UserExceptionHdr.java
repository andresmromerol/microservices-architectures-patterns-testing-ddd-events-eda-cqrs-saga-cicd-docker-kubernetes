package com.amr.shop.usr.user_context._shared.infrastructure.exception;

import static com.amr.shop.usr.user_context._shared.infrastructure.exception.UserBusinessCode.*;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;
import com.amr.shop.usr.user_context.user.domain.UserAlreadyExistsException;
import com.amr.shop.usr.user_context.user.domain.UserException;
import com.amr.shop.usr.user_context.user.domain.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
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
public class UserExceptionHdr {

  @ExceptionHandler(UserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleUserException(UserException exception) {
    log.error("UserException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, USER_ERROR, exception.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionDTO handleUserNotFoundException(UserNotFoundException exception) {
    log.error("UserNotFoundException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.NOT_FOUND, USER_NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ExceptionDTO handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    log.error("UserAlreadyExistsException occurred: {}", exception.getMessage());
    return buildExceptionDTO(HttpStatus.CONFLICT, USER_ALREADY_EXISTS, exception.getMessage());
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

    log.error("Binding errors occurred: {}", errors);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleConstraintViolationException(ConstraintViolationException ex) {
    List<String> errors =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

    log.error("Constraint violation errors occurred: {}", errors);
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.error("Message not readable error occurred: {}", ex.getMessage());
    return buildExceptionDTO(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, "Invalid request format");
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    log.error("Missing parameter error occurred: {}", ex.getMessage());
    return buildExceptionDTO(
        HttpStatus.BAD_REQUEST,
        VALIDATION_ERROR,
        String.format("Missing required parameter: %s", ex.getParameterName()));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionDTO handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.error("Type mismatch error occurred: {}", ex.getMessage());
    return buildExceptionDTO(
        HttpStatus.BAD_REQUEST,
        VALIDATION_ERROR,
        String.format("Invalid value for parameter: %s", ex.getName()));
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
