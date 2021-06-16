package com.app.ktu.dto.comment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotePatchDTO {

  @Max(value = 1, message = "Reikšmė negali viršyti 1")
  @Min(value = -1, message = "Reikšmė negali būti mažesnė už -1")
  private long vote;

}
