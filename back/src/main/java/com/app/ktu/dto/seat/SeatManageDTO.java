package com.app.ktu.dto.seat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatManageDTO {

  private long id;

  @NotBlank(message = "Vietos pavadinimas yra privalomas")
  @Size(max = 40, message = "Vietos pavadinimas yra per ilgas, turi būti iki 40 simbolių")
  private String name;

  @NotBlank(message = "Vietos kodas yra privalomas")
  @Size(max = 40, message = "Vietos kodas yra per ilgas, turi būti iki 40 simbolių")
  private String code;

}
