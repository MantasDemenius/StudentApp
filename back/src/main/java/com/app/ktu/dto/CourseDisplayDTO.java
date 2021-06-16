package com.app.ktu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDisplayDTO {

  private long id;

  @NotBlank(message = "Kodas yra privalomas")
  @Size(max = 40, message = "Kodas yra per ilgas, turi būti iki 40 simbolių")
  private String code;

  @NotBlank(message = "Pavadinimas yra privalomas")
  @Size(max = 40, message = "Pavadinimas yra per ilgas, turi būti iki 40 simbolių")
  private String title;

  @NotBlank(message = "Kalba yra privaloma")
  @Size(max = 40, message = "Kalba yra per ilga, turi būti iki 40 simbolių")
  private String language;
}
