package com.bebrample.diva.common.exception;


import java.time.LocalDateTime;

public record ErrorResponse(int status,
                            LocalDateTime timestamp,
                            String error,
                            String message) {

    public ErrorResponse(int status, String error, String message){
        this(status, LocalDateTime.now(), message, error);
    }
}
