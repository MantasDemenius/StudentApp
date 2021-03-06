package com.app.ktu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  public BadRequestException(String exception) {
    super(exception);
  }

  public BadRequestException() {
    super("You made a bad request");
  }

}
