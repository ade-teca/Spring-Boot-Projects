package com.keisar.Library.Management.Application.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String errorMessage;
    private String errorCode;
    private String errorDescription;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorMessage, String errorCode, String errorDescription) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.timestamp = LocalDateTime.now();
    }
}
