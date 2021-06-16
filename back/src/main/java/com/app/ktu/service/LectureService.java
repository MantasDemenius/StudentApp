package com.app.ktu.service;

import com.app.ktu.dto.LectureDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Room;
import com.app.ktu.model.Seat;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.security.AuthorizedUser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

  private SeatService seatService;

  @Autowired
  public void setSeatService(SeatService seatService) {
    this.seatService = seatService;
  }

  @Transactional
  public ResponseEntity<LectureDTO> getActiveLecture() {
    long userId = AuthorizedUser.getAuthorizedUserId();
    SeatUsage activeSeat = seatService.getSeatUsageByUserIdAndEndDateIsNull(userId);
    if (activeSeat == null) {
      return new ResponseEntity<LectureDTO>(HttpStatus.OK);
    }
    Course course = activeSeat.getCourseUsage().getCourse();
    Room room = activeSeat.getSeat().getRoom();
    Seat seat = activeSeat.getSeat();
    CourseUsage courseUsage = activeSeat.getCourseUsage();

    LectureDTO lecture = new LectureDTO();

    lecture.setCourseCode(course.getCode());
    lecture.setCourseTitle(course.getTitle());
    lecture.setRoomBuilding(room.getBuilding());
    lecture.setRoomNumber(room.getRoomNumber());
    lecture.setSeat(seat.getName());
    lecture.setCourseStartDate(courseUsage.getStartDate());
    lecture.setCourseEndDate(courseUsage.getEndDate());
    lecture.setUserOccupyStartDate(activeSeat.getStartDate());

    return new ResponseEntity<LectureDTO>(lecture, HttpStatus.OK);
  }

}
