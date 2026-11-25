package com.bebrample.diva.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleAllUncaughtExceptions() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.toString(),
                "Unhandled exception"
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleResourcesNotFound(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(DuplicateResourcesException.class)
    public ResponseEntity<ErrorResponse> HandleDuplicates(Exception e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, status);

    }

    @ExceptionHandler(NotEnoughAuthoritiesException.class)
    public ResponseEntity<ErrorResponse> HandleNotEnoughAuthorities(Exception e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, status);
    }
}
