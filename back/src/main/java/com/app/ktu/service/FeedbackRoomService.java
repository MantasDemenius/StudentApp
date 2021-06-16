package com.app.ktu.service;

import com.app.ktu.common.DateCheck;
import com.app.ktu.common.LocationHeader;
import com.app.ktu.dto.feedbackRoom.FeedbackRoomStagesDTO;
import com.app.ktu.dto.stage.StageDTO;
import com.app.ktu.exception.BadRequestException;
import com.app.ktu.exception.NotAllowedException;
import com.app.ktu.exception.RecordNotFoundException;
import com.app.ktu.model.Course;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.Stage;
import com.app.ktu.model.User;
import com.app.ktu.repository.FeedbackRoomRepository;
import com.app.ktu.repository.StageRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeedbackRoomService {

  private StageRepository stageRepository;
  private FeedbackRoomRepository feedbackRoomRepository;
  private UserService userService;
  private CourseService courseService;

  @Autowired
  public void setFeedbackRoomRepository(FeedbackRoomRepository feedbackRoomRepository) {
    this.feedbackRoomRepository = feedbackRoomRepository;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }

  @Autowired
  public void setStageRepository(StageRepository stageRepository) {
    this.stageRepository = stageRepository;
  }

  public FeedbackRoom getFeedbackRoomById(long FeedbackRoomId) {
    return feedbackRoomRepository.findById(FeedbackRoomId)
      .orElseThrow(
        () -> new RecordNotFoundException(
          "Atsiliepimų kambarys su " + FeedbackRoomId + " id buvo nerastas"));
  }

  public List<FeedbackRoom> getFeedbackRooms() {
    return feedbackRoomRepository.findAllByOrderByStartDateDesc();
  }

  @Transactional
  public ResponseEntity createFeedbackRoom(FeedbackRoomStagesDTO feedbackRoomStagesDTO) {
    FeedbackRoom feedbackRoom = new FeedbackRoom();
    ModelMapper modelMapper = new ModelMapper();

    if (feedbackRoomRepository
      .checkInBetween(feedbackRoomStagesDTO.getStartDate(), feedbackRoomStagesDTO.getEndDate())) {
      throw new NotAllowedException("Šita data jau egzistuoja atsiliepimų kambarys.");
    }

    List<StageDTO> stageDTOs = feedbackRoomStagesDTO.getStages();
    areStagesOutOfBounds(stageDTOs, feedbackRoomStagesDTO.getStartDate(),
      feedbackRoomStagesDTO.getEndDate());

    List<Course> courses = courseService.getCoursesUsedInDateRange(
      feedbackRoomStagesDTO.getStartDate(),
      feedbackRoomStagesDTO.getEndDate());
    Set<User> users = Set.copyOf(userService.getAllUsersByCourses(courses));

    feedbackRoom.setStartDate(feedbackRoomStagesDTO.getStartDate());
    feedbackRoom.setEndDate(feedbackRoomStagesDTO.getEndDate());
    feedbackRoom.setUsers(users);
    feedbackRoom.setCourses(Set.copyOf(courses));
    FeedbackRoom createdFeedbackRoom = feedbackRoomRepository.save(feedbackRoom);

    List<Stage> stages = new ArrayList<>();
    for (StageDTO stage : stageDTOs) {
      Stage stageToAdd = modelMapper.map(stage, Stage.class);
      stageToAdd.setFeedbackRoom(createdFeedbackRoom);
      stages.add(stageToAdd);
    }
    stageRepository.saveAll(stages);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/feedbackRooms", feedbackRoom.getId()),
      HttpStatus.CREATED);
  }

  public ResponseEntity deleteFeedbackRoomById(long feedbackRoomId) {
    try {
      feedbackRoomRepository.deleteById(feedbackRoomId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new BadRequestException("Atsiliepimų kambarys negalėjo būti ištrintas");
    }
  }

  @Transactional
  public ResponseEntity updateFeedbackRoom(FeedbackRoomStagesDTO feedbackRoomStagesDTO,
    long feedbackRoomId) {
    FeedbackRoom feedbackRoom = getFeedbackRoomById(feedbackRoomId);
    List<StageDTO> stageDTOs = feedbackRoomStagesDTO.getStages();
    areStagesOutOfBounds(stageDTOs, feedbackRoomStagesDTO.getStartDate(),
      feedbackRoomStagesDTO.getEndDate());

    feedbackRoom.setStartDate(feedbackRoomStagesDTO.getStartDate());
    feedbackRoom.setEndDate(feedbackRoomStagesDTO.getEndDate());

    for (Stage stage : feedbackRoom.getStages()) {
      for (StageDTO stageDTO : feedbackRoomStagesDTO.getStages()) {
        if (stage.getName().equals(stageDTO.getName())) {
          stage.setStartDate(stageDTO.getStartDate());
          stage.setEndDate(stageDTO.getEndDate());
        }
      }
    }

    feedbackRoomRepository.save(feedbackRoom);
    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/feedbackRooms", feedbackRoom.getId()),
      HttpStatus.CREATED);
  }

  public FeedbackRoom getActiveFeedbackRoom() {
    LocalDateTime now = LocalDateTime.now();
    return feedbackRoomRepository.findByStartDateBeforeAndEndDateAfter(now, now)
      .orElseThrow(
        () -> new RecordNotFoundException("Šiuo metu nevyksta joks atsiliepimų kambarys"));
  }

  private void areStagesOutOfBounds(List<StageDTO> stages, LocalDateTime startDate,
    LocalDateTime endDate) {
    List<StageDTO> stagesOutOfBounds = stages.stream().filter(stage -> !DateCheck
      .isLocalDateTimeEqualOrBetween(stage.getStartDate(),
        startDate,
        endDate)
      || !DateCheck
      .isLocalDateTimeEqualOrBetween(stage.getEndDate(),
        startDate,
        endDate)).collect(Collectors.toList());

    if (!stagesOutOfBounds.isEmpty()) {
      StringBuilder errorStageNames = new StringBuilder();
      stagesOutOfBounds.forEach(s -> errorStageNames.append(s.getName()).append(" "));
      throw new BadRequestException(
        "Etapai: [ " + errorStageNames + "], išeina iš atsiliepimo kambario ribų.");
    }
  }
}
