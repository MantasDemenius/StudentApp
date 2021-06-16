package com.app.ktu.common;

import java.time.LocalDateTime;

public class DateCheck {

  public static boolean isLocalDateTimeEqualOrBetween(LocalDateTime date, LocalDateTime startDate,
    LocalDateTime endDate) {
    if (date.isEqual(startDate) || date.isEqual(endDate)) {
      return true;
    }
    return date.isAfter(startDate) && date.isBefore(endDate);
  }

}
