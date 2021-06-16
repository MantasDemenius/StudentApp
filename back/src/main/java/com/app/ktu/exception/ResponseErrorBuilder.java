package com.app.ktu.exception;

import com.app.ktu.model.ApiError;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ResponseErrorBuilder {

  public static ApiError build(Exception ex, HttpStatus status) {
    List<String> errors = new ArrayList<String>();

    errors.add(ex.getMessage());

    return new ApiError(
      LocalDateTime.now(),
      status,
      errors
    );
  }

  public static ApiError build(String customMessage, HttpStatus status) {
    List<String> errors = new ArrayList<String>();

    errors.add(customMessage);

    return new ApiError(
      LocalDateTime.now(),
      status,
      errors
    );
  }
}
