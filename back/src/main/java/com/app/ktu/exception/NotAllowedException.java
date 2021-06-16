package com.app.ktu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowedException extends RuntimeException {

  public NotAllowedException(String exception) {
    super(exception);
  }

  public NotAllowedException() {
    super("We are working on it");
  }
}
