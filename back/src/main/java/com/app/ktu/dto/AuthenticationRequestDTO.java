package com.app.ktu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDTO {

  @NotBlank(message = "Prisijungimo vardas yra privalomas")
  @Size(max = 40, message = "Prisijungimo vardas yra per ilgas, turi būti iki 40 simbolių")
  private String username;

  @NotBlank(message = "Slaptažodis yra privalomas")
  @Pattern(regexp = "[a-zA-Z]", message = "Slaptažodis turi susidaryti iš lotyniškų raidžių")
  private String password;


}
