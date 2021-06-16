package com.app.ktu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {

  private String token;
  @JsonProperty("refresh_token")
  private String refreshToken;
}
