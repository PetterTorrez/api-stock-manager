package com.bluedot.stock_manager.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
    EntityNotFoundException ex
  ) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ErrorResponse.builder()
        .error("Not Found")
        .message(ex.getMessage())
        .build()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
    MethodArgumentNotValidException ex
  ) {
    String errorMessage = ex
      .getBindingResult()
      .getFieldError()
      .getDefaultMessage();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      ErrorResponse.builder().error("Bad Request").message(errorMessage).build()
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(
    IllegalArgumentException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
      ErrorResponse.builder().error("Conflict").message(ex.getMessage()).build()
    );
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      ErrorResponse.builder()
        .error("Internal Error Server")
        .message(ex.getMessage())
        .build()
    );
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
    HttpRequestMethodNotSupportedException ex
  ) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
      ErrorResponse.builder()
        .error("Method Not Allowed")
        .message(ex.getMessage())
        .build()
    );
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(
    MethodArgumentTypeMismatchException ex
  ) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      ErrorResponse.builder()
        .error("Invalid Parameter Format")
        .message(
          "El parámetro proporcionado es inválido o tiene un formato incorrecto."
        )
        .build()
    );
  }
}
