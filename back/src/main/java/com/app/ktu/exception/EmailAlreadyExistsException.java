package com.app.ktu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(String exception) {
    super(exception);
  }

  public EmailAlreadyExistsException() {
    super("We are working on it");
  }
}

