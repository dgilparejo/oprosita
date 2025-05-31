package com.oprosita.backend.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingRequestParam(MissingServletRequestParameterException ex) {
        String message = "Falta el parámetro obligatorio: " + ex.getParameterName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Método no permitido: " + ex.getMethod());
    }

    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllUncaughtExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toEpochMilli());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "Ha ocurrido un error inesperado. Contacta con soporte.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
    */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toEpochMilli());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<String> handleUnrecognizedProperty(UnrecognizedPropertyException ex) {
        String unknownField = ex.getPropertyName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Campo no reconocido en la petición: \"" + unknownField + "\". Por favor, revisa el cuerpo del JSON.");
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<String> handleTypeMismatch(MismatchedInputException ex) {
        return ResponseEntity.badRequest().body("Error de tipo en los datos enviados: " + ex.getOriginalMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("El JSON enviado no es válido: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Validation Failed");
        body.put("violations", ex.getConstraintViolations()
                .stream()
                .map(violation -> Map.of(
                        "property", violation.getPropertyPath().toString(),
                        "message", violation.getMessage()
                ))
                .collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Conflicto de datos",
                        "message", ex.getMessage()
                ));
    }
}
