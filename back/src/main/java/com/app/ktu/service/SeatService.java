package com.app.ktu.service;

import com.app.ktu.common.QRCodeGeneration;
import com.app.ktu.dto.EndDateDTO;
import com.app.ktu.dto.LectureDTO;
import com.app.ktu.dto.seat.OccupySeatDTO;
import com.app.ktu.dto.seat.SeatCreateDTO;
import com.app.ktu.exception.BadRequestException;
import com.app.ktu.exception.NotAllowedException;
import com.app.ktu.exception.RecordNotFoundException;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Room;
import com.app.ktu.model.Seat;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.model.User;
import com.app.ktu.repository.SeatRepository;
import com.app.ktu.repository.SeatUsageRepository;
import com.app.ktu.security.AuthorizedUser;
import com.app.ktu.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

  @Autowired
  private SeatRepository seatRepository;
  @Autowired
  private SeatUsageRepository seatUsageRepository;
  @Autowired
  private RoomService roomService;
  @Autowired
  private CourseService courseService;

  public void setSeatRepository(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
  }

  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }

  public void setRoomService(RoomService roomService) {
    this.roomService = roomService;
  }

  public void setSeatUsageRepository(SeatUsageRepository seatUsageRepository) {
    this.seatUsageRepository = seatUsageRepository;
  }

  public List<Seat> getRoomSeats(long roomId) {
    return seatRepository.findAllByRoomIdOrderByName(roomId);
  }

  public Seat getSeatByCode(String code) {
    return seatRepository.findByCode(code)
      .orElseThrow(() -> new BadRequestException("Vieta neegzistuoja"));
  }

  public SeatUsage getActiveSeatUsageByUserId(long userId) {
    return seatUsageRepository.findByUserIdAndEndDateIsNull(userId)
      .orElseThrow(() -> new BadRequestException("Jūs neesate užėmes vietos"));
  }

  public List<Seat> createSeats(long roomId, SeatCreateDTO seatsToGenerate) {
    Room room = roomService.getRoomById(roomId);
    int seatCount = seatRepository.countByRoomId(roomId) + 1;
    List<Seat> seats = new ArrayList<Seat>();

    for (int i = seatCount; i < seatCount + seatsToGenerate.getQuantity(); i++) {
      Seat seat = new Seat();
      String uniqueQRCodeId = UUID.randomUUID().toString();

      seat.setName(room.getRoomNumber() + " - " + i);
      seat.setCode(uniqueQRCodeId);
      seat.setIsLocked(false);
      seat.setRoom(room);

      seats.add(seat);
    }

    seatRepository.saveAll(seats);

    return seats;
  }

  public ResponseEntity<byte[]> qrCodeGeneration(long roomId, long seatId) {
    Seat seat = seatRepository.findByIdAndRoomId(seatId, roomId)
      .orElseThrow(
        () -> new RecordNotFoundException("Vieta su " + seatId + " id buvo nerasta"));

    byte[] qrCode = QRCodeGeneration.generateQRCodeImage(seat.getCode());

    return new ResponseEntity<>(qrCode, HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<LectureDTO> occupySeat(OccupySeatDTO occupySeatDTO) {
    LocalDateTime now = LocalDateTime.now();
    User user = AuthorizedUser.getAuthorizedUser();
    Seat seat = getSeatByCode(occupySeatDTO.getCode());
    Room room = seat.getRoom();
    List<CourseUsage> activeCourseUsages = courseService.getActiveCourseUsages(now);

    CourseUsage courseUsage = activeCourseUsages
      .stream()
      .filter(cu -> cu.getRoom().getId() == room.getId())
      .findFirst()
      .orElseThrow(() -> new RecordNotFoundException("Šioje vietoje nevyksta jokia paskaita"));

    Course course = courseUsage.getCourse();

    courseService.isUserAllowedToAccessCourse(course.getId());
    isUserNotOccupyingSeat(user);

    if (seat.getIsLocked()) {
      throw new NotAllowedException("Vieta jau užimta");
    }

    SeatUsage occupySeat = new SeatUsage();
    occupySeat.setCourseUsage(courseUsage);
    occupySeat.setSeat(seat);
    occupySeat.setUser(user);
    occupySeat.setStartDate(now);

    seat.setIsLocked(true);
    seatRepository.save(seat);
    seatUsageRepository.save(occupySeat);

    LectureDTO lecture = new LectureDTO();
    lecture.setCourseCode(course.getCode());
    lecture.setCourseTitle(course.getTitle());
    lecture.setRoomBuilding(room.getBuilding());
    lecture.setRoomNumber(room.getRoomNumber());
    lecture.setSeat(seat.getName());
    lecture.setCourseStartDate(courseUsage.getStartDate());
    lecture.setCourseEndDate(courseUsage.getEndDate());
    lecture.setUserOccupyStartDate(now);

    return new ResponseEntity<LectureDTO>(lecture, HttpStatus.OK);
  }

  public void isUserNotOccupyingSeat(User user) {
    LocalDateTime now = LocalDateTime.now();
    List<CourseUsage> activeCourseUsages = courseService.getActiveCourseUsages(now);
    for (CourseUsage activeCourseUsage : activeCourseUsages) {
      for (SeatUsage seatUsage : activeCourseUsage.getSeatUsages()) {
        if (seatUsage.getEndDate() == null && seatUsage.getUser().equals(user)) {
          throw new NotAllowedException("Jau esate užėmęs vietą");
        }
      }
    }
  }

  @Transactional
  public ResponseEntity leaveSeat() {
    LocalDateTime now = LocalDateTime.now();
    long userId = AuthorizedUser.getAuthorizedUserId();
    SeatUsage seatUsage = getActiveSeatUsageByUserId(userId);
    Seat seat = seatUsage.getSeat();

    seat.setIsLocked(false);
    seatUsage.setEndDate(now);

    seatRepository.save(seat);
    seatUsageRepository.save(seatUsage);

    return new ResponseEntity(HttpStatus.OK);
  }

  public ResponseEntity deleteSeat(long seatId) {
    try {
      seatRepository.deleteById(seatId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
      throw new BadRequestException("Ši vieta yra užimta");
    } catch (Exception ex) {
      throw new RecordNotFoundException("Vieta buvo nerasta");
    }
  }

  public SeatUsage getSeatUsageByUserIdAndEndDateIsNull(long userId) {
    return seatUsageRepository.findByUserIdAndEndDateIsNull(userId).orElse(null);
  }

  public ResponseEntity clearSeat(EndDateDTO endDateDTO, long seatId) {
    Seat seat = getSeatById(seatId);

    SeatUsage seatUsage = seat.getSeatUsages().stream().filter(su -> su.getEndDate() == null)
      .findFirst().orElseThrow(() -> new RecordNotFoundException("Ši vieta neužimta"));
    seatUsage.setEndDate(endDateDTO.getEndDate());
    seat.setIsLocked(false);
    seatUsageRepository.save(seatUsage);
    seatRepository.save(seat);

    return new ResponseEntity(HttpStatus.OK);
  }

  private Seat getSeatById(long seatId) {
    return seatRepository.findById(seatId)
      .orElseThrow(() -> new RecordNotFoundException("Vieta su " + seatId + " id buvo nerasta"));
  }

  public ResponseEntity lockSeat(long seatId, boolean isLocked) {
    Seat seat = getSeatById(seatId);
    seat.setIsLocked(isLocked);
    seatRepository.save(seat);
    return new ResponseEntity(HttpStatus.OK);
  }

  public List<SeatUsage> getCourseSeatUsages(long courseId) {
    Principal principal = AuthorizedUser.getAuthorizedPrincipal();
    long userId = principal.getUser().getId();

    List<SeatUsage> seatUsages = seatUsageRepository.findAllCourseSeatUsages(courseId, userId);

    boolean isAllowed = principal.getAuthorities().stream()
      .anyMatch(
        o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_LECTURER"));

    List<SeatUsage> finalSeatUsages = new ArrayList<>();
    if (isAllowed) {
      finalSeatUsages.addAll(seatUsages);
    } else {
      boolean isStudent = principal.getAuthorities().stream()
        .anyMatch(o -> o.getAuthority().equals("ROLE_STUDENT"));
      if (isStudent) {
        finalSeatUsages.addAll(
          seatUsages.stream().filter(seatUsage -> seatUsage.getUser().getId() == userId)
            .collect(
              Collectors.toList()));
      }
    }

    return finalSeatUsages;
  }
}
