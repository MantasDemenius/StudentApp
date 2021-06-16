package com.app.ktu.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseSeatDTO {

  private String title;
  private UserDTO user;
  private String seatName;

}
