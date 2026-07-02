package ar.edu.utn.frba.ddsi.climalert.responses.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", ex.getStatus().value(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {

        return ResponseEntity
                .internalServerError()
                .body(Map.of(
                        "error", "Error interno del servidor",
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }
}