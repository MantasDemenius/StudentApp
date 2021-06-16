package com.app.ktu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

  public RecordNotFoundException(String exception) {
    super(exception);
  }

  public RecordNotFoundException() {
    super("We are working on it");
  }
}
