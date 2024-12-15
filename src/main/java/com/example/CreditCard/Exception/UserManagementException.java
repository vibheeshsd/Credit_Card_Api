package com.example.CreditCard.Exception;

public class UserManagementException extends RuntimeException {
  public UserManagementException(String message) {
        super(message);
    }

  public UserManagementException() {
  }

  public UserManagementException(Throwable cause) {
    super(cause);
  }

  public UserManagementException(String message, Throwable cause) {
    super(message, cause);
  }

}
