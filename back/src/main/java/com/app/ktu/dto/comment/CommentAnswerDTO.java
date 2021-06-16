package com.app.ktu.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAnswerDTO {

  @Size(max = 2000, message = "Atsiliepimas yra per ilgas, turi būti iki 2000 simbolių")
  private String comment;

  @Size(max = 2000, message = "Atsakymas yra per ilgas, turi būti iki 2000 simbolių")
  private String answer;

  @NotBlank(message = "Statusas yra privalomas")
  private String status;

}
