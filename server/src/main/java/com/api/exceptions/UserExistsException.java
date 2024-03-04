package com.api.exceptions;

public class UserExistsException extends Exception{
  public UserExistsException(String msg) {
    super(msg);
  }
}
