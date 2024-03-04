package com.api.exceptions;

public class WrongPhoneNumberException extends RuntimeException {
  public WrongPhoneNumberException(String msg) {
    super(msg);
  }
}
