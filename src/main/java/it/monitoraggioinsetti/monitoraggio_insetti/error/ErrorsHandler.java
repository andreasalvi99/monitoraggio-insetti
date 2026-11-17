package it.monitoraggioinsetti.monitoraggio_insetti.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
