package com.app.ktu.common.converter;

import com.app.ktu.dto.seat.SeatUsageDTO;
import com.app.ktu.model.Seat;
import com.app.ktu.model.SeatUsage;
import org.modelmapper.AbstractConverter;

public class SeatToSeatUsageDTOConverter extends AbstractConverter<Seat, SeatUsageDTO> {

  @Override
  protected SeatUsageDTO convert(Seat seat) {
    SeatUsageDTO seatUsageDTO = new SeatUsageDTO();
    SeatUsage seatUsage;

    seatUsage = seat.getSeatUsages().stream().filter(su -> su.getEndDate() == null).findFirst()
      .orElse(null);
    seatUsageDTO.setSeatId(seat.getId());
    seatUsageDTO.setSeatName(seat.getName());
    seatUsageDTO.setLocked(seat.getIsLocked());
    if (seatUsage != null) {
      seatUsageDTO.setUserName(seatUsage.getUser().getName());
      seatUsageDTO.setUserSurname(seatUsage.getUser().getSurname());
      seatUsageDTO.setStartDate(seatUsage.getStartDate());
    }
    return seatUsageDTO;
  }
}
