package com.app.ktu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

  private long id;
  @NotBlank(message = "Adresas yra privalomas")
  @Size(max = 40, message = "Adresas yra per ilgas, turi būti iki 40 simbolių")
  private String address;

  @NotBlank(message = "Pastatas yra privalomas")
  @Size(max = 40, message = "Pastatas yra per ilgas, turi būti iki 40 simbolių")
  private String building;

  @NotBlank(message = "Auditorijos numeris yra privalomas")
  @Size(max = 40, message = "Auditorijos numeris yra per ilgas, turi būti iki 40 simbolių")
  private String roomNumber;

  @NotBlank(message = "Tipas yra privalomas")
  @Size(max = 40, message = "Tipas per ilgas, turi būti iki 40 simbolių")
  private String type;

}
