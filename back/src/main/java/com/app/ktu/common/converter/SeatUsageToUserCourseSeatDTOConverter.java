package com.app.ktu.common.converter;

import com.app.ktu.dto.user.UserCourseSeatDTO;
import com.app.ktu.dto.user.UserDTO;
import com.app.ktu.model.SeatUsage;
import java.time.format.DateTimeFormatter;
import org.modelmapper.AbstractConverter;

public class SeatUsageToUserCourseSeatDTOConverter extends
  AbstractConverter<SeatUsage, UserCourseSeatDTO> {

  @Override
  protected UserCourseSeatDTO convert(SeatUsage seatUsage) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    UserCourseSeatDTO userCourseSeatDTO = new UserCourseSeatDTO();

    userCourseSeatDTO.setTitle(
      seatUsage.getCourseUsage().getStartDate().format(formatter) + " - "
        + seatUsage.getCourseUsage().getEndDate().format(formatter));
    userCourseSeatDTO.setSeatName(seatUsage.getSeat().getName());
    userCourseSeatDTO
      .setUser(new UserDTO(seatUsage.getUser().getName(), seatUsage.getUser().getSurname()));

    return userCourseSeatDTO;
  }
}
