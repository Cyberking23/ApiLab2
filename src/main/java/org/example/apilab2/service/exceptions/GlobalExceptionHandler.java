package org.example.apilab2.service.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> nf(NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of("NOT_FOUND", ex.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(ConflictException.class)
    ResponseEntity<ErrorResponse> cf(ConflictException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of("CONFLICT", ex.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> ve(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var details = ex.getBindingResult().getFieldErrors().stream().map(f -> f.getField()+": "+f.getDefaultMessage()).toList();
        return ResponseEntity.badRequest().body(ErrorResponse.of("VALIDATION_ERROR", "Datos inválidos", req.getRequestURI(), details));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> ge(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of("INTERNAL_ERROR", "Error inesperado", req.getRequestURI()));
    }
}
