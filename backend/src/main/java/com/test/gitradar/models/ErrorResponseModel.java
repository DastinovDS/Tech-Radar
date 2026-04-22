package com.test.gitradar.models;

import java.time.LocalDateTime;

public class ErrorResponseModel {
    private String message;
    private String errorSource;
    private LocalDateTime timestamp;

    public ErrorResponseModel(String message, String errorMessage, LocalDateTime timestamp) {
        this.message = message;
        this.errorSource = errorMessage;
        this.timestamp = timestamp;
    }

    public ErrorResponseModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorSource;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorSource = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
