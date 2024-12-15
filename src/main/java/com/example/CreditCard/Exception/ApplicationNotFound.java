package com.example.CreditCard.Exception;

public class ApplicationNotFound extends RuntimeException {
    public ApplicationNotFound(String message) {
        super(message);
    }

    public ApplicationNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotFound(Throwable cause) {
        super(cause);
    }

    public ApplicationNotFound() {
    }

}
