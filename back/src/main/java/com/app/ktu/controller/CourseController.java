package com.app.ktu.controller;

import com.app.ktu.dto.CourseDisplayDTO;
import com.app.ktu.dto.TimeTableDTO;
import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.model.Course;
import com.app.ktu.service.CourseService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class CourseController {

  @Autowired
  ModelMapper modelMapper;


  private CourseService courseService;

  @Autowired
  public CourseService setCourseService(CourseService courseService) {
    return this.courseService = courseService;
  }

  @GetMapping("/courses")
  @PreAuthorize("hasAuthority('courses:read')")
  public List<CourseDisplayDTO> getUserCourses() {
    return convertCourseListToCourseDisplayDTOList(courseService.getUserCourses());
  }

  @GetMapping("/courses/{courseId}")
  @PreAuthorize("hasAuthority('courses:read')")
  public CourseDisplayDTO getCourse(@PathVariable(value = "courseId") long courseId) {
    return convertCourseToCourseDisplayDTO(courseService.getCourseById(courseId));
  }

  @GetMapping("/courses/timetable/{quantity}")
  @PreAuthorize("hasAuthority('courses:timetable')")
  public List<TimeTableDTO> getCoursesTimetable(@PathVariable(name = "quantity") long quantity) {
    return courseService.getCoursesTimetables(quantity);
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}/courses")
  @PreAuthorize("hasAuthority('feedbackCourses:read')")
  public List<SectionDTO> getFeedbackRoomCourses(
    @PathVariable(name = "feedbackRoomId") long feedbackRoomId) {
    return courseService.getFeebackRoomCourses(feedbackRoomId);
  }

  private List<CourseDisplayDTO> convertCourseListToCourseDisplayDTOList(List<Course> courses) {
    return courses.stream().map(this::convertCourseToCourseDisplayDTO)
      .collect(Collectors.toList());
  }

  private CourseDisplayDTO convertCourseToCourseDisplayDTO(Course course) {
    return modelMapper.map(course, CourseDisplayDTO.class);
  }

}
