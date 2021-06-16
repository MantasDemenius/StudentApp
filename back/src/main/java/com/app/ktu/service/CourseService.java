package com.app.ktu.service;

import com.app.ktu.dto.TimeTableDTO;
import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.exception.BadRequestException;
import com.app.ktu.exception.NotAllowedException;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseUsage;
import com.app.ktu.repository.CourseRepository;
import com.app.ktu.repository.CourseUsageRepository;
import com.app.ktu.security.AuthorizedUser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  private CourseRepository courseRepository;
  private CourseUsageRepository courseUsageRepository;

  @Autowired
  public void setCourseRepository(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Autowired
  public void setCourseUsageRepository(CourseUsageRepository courseUsageRepository) {
    this.courseUsageRepository = courseUsageRepository;
  }


  public List<Course> getUserCourses() {
    long userId = AuthorizedUser.getAuthorizedUserId();
    return courseRepository.findAllByUserVisibilitiesIdOrderByTitle(userId);
  }

  public Course getCourseById(long courseId) {
    isUserAllowedToAccessCourse(courseId);
    return courseRepository.findById(courseId)
      .orElseThrow(() -> new BadRequestException("Modulis neegzistuoja"));
  }

  public List<CourseUsage> getActiveCourseUsages(LocalDateTime now) {
    List<CourseUsage> courseUsages = courseUsageRepository.findAll();
    List<CourseUsage> activeCourseUsages = courseUsages
      .stream()
      .filter(cu -> now.isAfter(cu.getStartDate()) && now.isBefore(cu.getEndDate()))
      .collect(
        Collectors.toList());
    if (activeCourseUsages.isEmpty()) {
      throw new NotAllowedException("Šiuo metu nevyksta paskaita");
    }
    return activeCourseUsages;
  }

  public void isUserAllowedToAccessCourse(long courseId) throws AccessDeniedException{
    long userId = AuthorizedUser.getAuthorizedUserId();
    if (!courseRepository.isUserAllowedToAccessCourse(userId, courseId)) {
      throw new AccessDeniedException("Naudotojas neturi teisių prieiti prie šio modulio");
    }
  }

  public List<TimeTableDTO> getCoursesTimetables(long quantity) {
    long userId = AuthorizedUser.getAuthorizedUserId();
    List<CourseUsage> courseUsages = courseUsageRepository
      .findAllUserCoursesFromNow(userId, quantity);

    return courseUsages.stream().map(
      cu -> new TimeTableDTO(
        cu.getId(),
        cu.getCourse().getTitle(),
        cu.getRoom().getId(),
        cu.getRoom().getBuilding(),
        cu.getRoom().getRoomNumber(),
        cu.getStartDate(),
        cu.getEndDate()
      )).collect(Collectors.toList());
  }

  public List<Course> getCoursesUsedInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    return courseRepository.findAllCoursesInDateRange(startDate, endDate);
  }

  public List<SectionDTO> getFeebackRoomCourses(long feedbackRoomId) {
    long userId = AuthorizedUser.getAuthorizedUserId();
    List<Course> courses = courseRepository.findAllFeedbackRoomCourses(feedbackRoomId, userId);

    List<SectionDTO> sectionDTOS = new ArrayList<>();
    courses.forEach(course -> {
      SectionDTO sectionDTO = new SectionDTO();
      sectionDTO.setId(course.getId());
      sectionDTO.setTitle(course.getTitle() + ' ' + course.getCode());

      long commentCount = course.getComments().stream()
        .filter(courseComment -> courseComment.getFeedbackRoom().getId() == feedbackRoomId
          && !courseComment.getIsDeleted()).count();
      sectionDTO.setCommentCount(commentCount);
      sectionDTOS.add(sectionDTO);
    });

    return sectionDTOS;
  }
}
