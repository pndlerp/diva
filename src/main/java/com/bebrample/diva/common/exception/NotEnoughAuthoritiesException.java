package com.bebrample.diva.common.exception;

public class NotEnoughAuthoritiesException extends RuntimeException {
    public NotEnoughAuthoritiesException(String message) {
        super(message);
    }
}
