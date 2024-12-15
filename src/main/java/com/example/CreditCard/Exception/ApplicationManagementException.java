package com.example.CreditCard.Exception;

public class ApplicationManagementException extends RuntimeException {
    public ApplicationManagementException(String message) {
        super(message);
    }

    public ApplicationManagementException() {
    }

    public ApplicationManagementException(Throwable cause) {
        super(cause);
    }

    public ApplicationManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
