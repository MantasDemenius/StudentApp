package com.app.ktu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {

  public UsernameAlreadyExistsException(String exception) {
    super(exception);
  }

  public UsernameAlreadyExistsException() {
    super("We are working on it");
  }
}

