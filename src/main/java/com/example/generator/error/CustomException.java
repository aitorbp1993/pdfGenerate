package com.example.generator.error;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    private final HttpStatus status;

    // Constructor para mensaje y HttpStatus
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    // Constructor para mensaje y Throwable
    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR; // O el estado que prefieras
    }

    // Constructor solo para mensaje
    public CustomException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // O el estado que prefieras
    }

    public HttpStatus getStatus() {
        return status;
    }
}
