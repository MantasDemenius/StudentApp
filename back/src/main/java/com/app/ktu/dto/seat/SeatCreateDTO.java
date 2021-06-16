package com.app.ktu.dto.seat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatCreateDTO {

  @Min(value = 1, message = "Turi būti kuriama bent viena vieta")
  @Max(value = 1000, message = "Kuriama per daug vietų")
  private long quantity;
}
