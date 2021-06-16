package com.app.ktu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ktu.dto.TimeTableDTO;
import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.CourseCommentVote;
import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Email;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.Role;
import com.app.ktu.model.Room;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.model.User;
import com.app.ktu.model.UserComment;
import com.app.ktu.model.UserCommentVote;
import com.app.ktu.model.UserCourseResponsibility;
import com.app.ktu.repository.CourseRepository;
import com.app.ktu.repository.CourseUsageRepository;
import com.app.ktu.security.Principal;
import helper.ExpectedResults;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

class CourseServiceTest {


  @InjectMocks
  private CourseService courseServiceUnderTest;
  @Mock
  private CourseRepository mockCourseRepository;
  @Mock
  private CourseUsageRepository mockCourseUsageRepository;
  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    courseServiceUnderTest = new CourseService();
    courseServiceUnderTest.setCourseRepository(mockCourseRepository);
    courseServiceUnderTest.setCourseUsageRepository(mockCourseUsageRepository);
    SecurityContextHolder.getContext().setAuthentication(
      new UsernamePasswordAuthenticationToken(getPrincipal(), "password"));
  }

  @Test
  void getUserCourses_HasCourses_CoursesList() {
    // Setup
    List<CourseUsage> usages = new ArrayList<CourseUsage>();
    List<UserCourseResponsibility> responsibilities = new ArrayList<>();
    List<CourseComment> comments = new ArrayList<>();
    Set<User> userVisibilities = new HashSet<>();
    Course expectedResult = new Course(0L, null, null, null, usages, responsibilities, comments,
      userVisibilities);
    when(mockCourseRepository.findAllByUserVisibilitiesIdOrderByTitle(0L))
      .thenReturn(List.of(expectedResult));
    // Run the test
    final List<Course> result = courseServiceUnderTest.getUserCourses();

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  @Test
  void getCourseById_CourseExists_Course() {
    // Setup
    List<CourseUsage> usages = new ArrayList<CourseUsage>();
    List<UserCourseResponsibility> responsibilities = new ArrayList<>();
    List<CourseComment> comments = new ArrayList<>();
    Set<User> userVisibilities = new HashSet<>();

    Course expectedResult = new Course(0L, null, null, null, usages, responsibilities, comments,
      userVisibilities);
    mockFindByCourseId();
    allowAccess();
    // Run the test
    final Course result = courseServiceUnderTest.getCourseById(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getActiveCourseUsages_ActiveCourseUsageExists_CourseUsageList() {
    // Setup
    final LocalDateTime now = LocalDateTime.of(2020, 1, 1, 8, 1);
    CourseUsage expectedResult = getExpectedCourseUsage();
    mockFindAllCourseUsages();
    // Run the test
    final List<CourseUsage> result = courseServiceUnderTest.getActiveCourseUsages(now);

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  @Test
  void isUserAllowedToAccessCourse_Allowed__() {
    // Setup
    allowAccess();
    // Run the test
    courseServiceUnderTest.isUserAllowedToAccessCourse(0L);

    // Verify the results
    verify(mockCourseRepository).isUserAllowedToAccessCourse(0L, 0L);
  }

  @Test
  void getCoursesTimetables_CourseUsageExists_TimeTableDTOList() {
    // Setup
    allowAccess();
    CourseUsage courseUsage = getExpectedCourseUsage();
    when(mockCourseUsageRepository.findAllUserCoursesFromNow(0L, 1L))
      .thenReturn(List.of(courseUsage));

    TimeTableDTO expectedResult = new TimeTableDTO(
      courseUsage.getId(),
      courseUsage.getCourse().getTitle(),
      courseUsage.getRoom().getId(),
      courseUsage.getRoom().getBuilding(),
      courseUsage.getRoom().getRoomNumber(),
      courseUsage.getStartDate(),
      courseUsage.getEndDate());
    // Run the test
    final List<TimeTableDTO> result = courseServiceUnderTest.getCoursesTimetables(1L);

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  @Test
  void getCoursesUsedInDateRange_CourseExists_CoursesList() {
    // Setup
    final LocalDateTime startDate = LocalDateTime.of(2018, 1, 1, 8, 1);
    final LocalDateTime endDate = LocalDateTime.of(2021, 1, 1, 8, 1);
    Course expectedResult = ExpectedResults.getCourse();
    when(mockCourseRepository.findAllCoursesInDateRange(startDate, endDate)).thenReturn(List.of(expectedResult));
    // Run the test
    final List<Course> result = courseServiceUnderTest
      .getCoursesUsedInDateRange(startDate, endDate);

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  @Test
  void gtFeebackRoomCourses_CoursesExist_SectionDTOList() {
    // Setup
    allowAccess();
    Course course = ExpectedResults.getCourse();
    when(mockCourseRepository.findAllFeedbackRoomCourses(0L, 0L)).thenReturn(List.of(course));
    List<SectionDTO> expectedResult = new ArrayList<>();
    expectedResult.add(new SectionDTO(0L, "title code", 0));
    // Run the test
    final List<SectionDTO> result = courseServiceUnderTest.getFeebackRoomCourses(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  void allowAccess() {
    when(mockCourseRepository.isUserAllowedToAccessCourse(anyLong(), anyLong())).thenReturn(true);
  }

  void mockFindAllCourses() {
    List<CourseUsage> usages = new ArrayList<CourseUsage>();
    List<UserCourseResponsibility> responsibilities = new ArrayList<>();
    List<CourseComment> comments = new ArrayList<>();
    Set<User> userVisibilities = new HashSet<>();

    Course expectedResult = new Course(0L, null, null, null, usages, responsibilities, comments,
      userVisibilities);
    when(mockCourseRepository.findAll()).thenReturn(List.of(expectedResult));
  }



  void mockFindAllCourseUsages() {
    when(mockCourseUsageRepository.findAll()).thenReturn(List.of(getExpectedCourseUsage()));
  }

  void mockFindByCourseId() {
    List<CourseUsage> usages = new ArrayList<CourseUsage>();
    List<UserCourseResponsibility> responsibilities = new ArrayList<>();
    List<CourseComment> comments = new ArrayList<>();
    Set<User> userVisibilities = new HashSet<>();

    Course expectedResult = new Course(0L, null, null, null, usages, responsibilities, comments,
      userVisibilities);
    when(mockCourseRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
  }

  Principal getPrincipal() {
    Set<Role> roles = new HashSet<>();
    Set<Course> courses = new HashSet<>();
    List<SeatUsage> seatUsages = new ArrayList<>();
    List<Email> emails = new ArrayList<Email>();
    List<UserCourseResponsibility> userCourseResponsibilities = new ArrayList<>();
    List<CourseComment> courseCommentAnswers = new ArrayList<>();
    List<CourseComment> courseCommentAskers = new ArrayList<>();
    List<CourseCommentVote> courseCommentVotes = new ArrayList<>();
    List<UserComment> userCommentAnswers = new ArrayList<>();
    List<UserComment> userCommentAskers = new ArrayList<>();
    List<UserComment> userCommentSubjects = new ArrayList<>();
    List<UserCommentVote> userCommentVotes = new ArrayList<>();
    Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    return new Principal(
      new User(0L, null, null, null, null, null, null, null, roles, courses, seatUsages, emails,
        userCourseResponsibilities,
        courseCommentAnswers, courseCommentAskers, courseCommentVotes, userCommentAnswers,
        userCommentAskers, userCommentSubjects, userCommentVotes), authorities);
  }

  CourseUsage getExpectedCourseUsage() {

    List<SeatUsage> seatUsages = new ArrayList<SeatUsage>();

    return new CourseUsage(0L, new Course(), new Room(), LocalDateTime.of(2019, 1, 1, 8, 1),
      LocalDateTime.of(2021, 1, 1, 8, 1), null, seatUsages
    );
  }


}
