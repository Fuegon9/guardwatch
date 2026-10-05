package com.example.guardwatch.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> manejarResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> manejarBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 403 — el usuario SÍ está autenticado pero su rol no permite esta operación.
    // @PreAuthorize lanza AccessDeniedException dentro del controller; sin este
    // handler la captura el de Exception.class y se devolvía 500 por error.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> manejarAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                "Acceso denegado: su rol no tiene permiso para realizar esta operación.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> manejarGlobalException(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}