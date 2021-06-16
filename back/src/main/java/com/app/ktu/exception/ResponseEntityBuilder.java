package com.app.ktu.exception;

import com.app.ktu.model.ApiError;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {

  public static ResponseEntity<Object> build(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
