package com.example.CreditCard.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

    private String errorMessage;
    private String message;

    // Constructor with @Value to inject the property value
    @Autowired
    public ErrorResponse(@Value("${error.message}") String message) {
        this.message = message;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
