package com.api.exceptions;

public class InvalidCredentialException extends RuntimeException {
  public InvalidCredentialException(String message) {
    super(message);
  }
}
