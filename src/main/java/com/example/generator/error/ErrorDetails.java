package com.example.generator.error;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetails {
    private Date timestamp;
    private int status;
    private String message;
    private String details;

    public ErrorDetails(int status, String message, String details) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
    }

}

