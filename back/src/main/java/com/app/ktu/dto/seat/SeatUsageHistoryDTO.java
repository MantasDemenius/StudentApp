package com.app.ktu.dto.seat;

import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Seat;
import com.app.ktu.model.User;
import org.springframework.beans.factory.annotation.Value;


public interface SeatUsageHistoryDTO {

  @Value("#{target.courseUsage}")
  CourseUsage getCourseUsage();

  @Value("#{target.seat}")
  Seat getSeat();

  @Value("#{target.user}")
  User getUser();
}