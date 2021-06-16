package com.app.ktu.service;

import static helper.MockServlet.mockServletContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ktu.dto.comment.CommentAnswerDTO;
import com.app.ktu.dto.comment.CommentCreateDTO;
import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VoteDTO;
import com.app.ktu.dto.user.UserDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.CourseCommentVote;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.User;
import com.app.ktu.repository.CourseCommentRepository;
import com.app.ktu.repository.CourseCommentVoteRepository;
import helper.ExpectedResults;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

class CourseCommentServiceTest {

  @InjectMocks
  private CourseCommentService courseCommentServiceUnderTest;
  @Mock
  private FeedbackRoomService mockFeedbackRoomService;
  @Mock
  private CourseService mockCourseService;
  @Mock
  private CourseCommentRepository mockCourseCommentRepository;
  @Mock
  private CourseCommentVoteRepository mockCourseCommentVoteRepository;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    courseCommentServiceUnderTest = new CourseCommentService();
    courseCommentServiceUnderTest.setFeedbackRoomService(mockFeedbackRoomService);
    courseCommentServiceUnderTest.setCourseService(mockCourseService);
    courseCommentServiceUnderTest.setCourseCommentRepository(mockCourseCommentRepository);
    courseCommentServiceUnderTest.setCourseCommentVoteRepository(mockCourseCommentVoteRepository);
    SecurityContextHolder.getContext().setAuthentication(
      new UsernamePasswordAuthenticationToken(ExpectedResults.getPrincipal(), "password"));
  }

  @AfterEach
  void closeService() throws Exception {
    closeable.close();
  }

  @Test
  void getCourseCommentById_CourseCommentExists_CourseComment() {
    // Setup
    CourseComment expectedResult = ExpectedResults.getCourseComment();
    mockFindByCourseCommentId();
    // Run the test
    final CourseComment result = courseCommentServiceUnderTest.getCourseCommentById(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getCourseCommentVoteById_CourseCommentVoteExists_CourseCommentVote() {
    // Setup
    CourseCommentVote expectedResult = new CourseCommentVote(0L, 0, new CourseComment(),
      new User());
    mockFindByCourseCommentId();
    // Run the test
    final CourseCommentVote result = courseCommentServiceUnderTest.getCourseCommentVoteById(0L, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getCourseComments_CourseCommentsExist_CourseComments() {
    // Setup
    CourseComment expectedResult = ExpectedResults.getCourseComment();
    mockFindAllCourseComment();
    // Run the test
    final List<CourseComment> result = courseCommentServiceUnderTest.getCourseComments();

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  @Test
  void createCourseComment_Success_StatusCreatedWithLocation() {
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("courseComments/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final CommentCreateDTO commentCreateDTO = new CommentCreateDTO("comment");
    mockCourse();
    mockFeedbackRoom();
    mockCourseCommentSave();
    // Run the test
    final ResponseEntity result = courseCommentServiceUnderTest
      .createCourseComment(0L, 0L, commentCreateDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteCourseCommentById_DeleteSuccess_StatusOK() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
    // Run the test
    final ResponseEntity result = courseCommentServiceUnderTest.deleteCourseCommentById(0L);

    // Verify the results
    verify(mockCourseCommentRepository).deleteById(0L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void createCourseCommentVote_Success_Null() {
    // Setup
    final CourseComment courseComment = ExpectedResults.getCourseComment();
    mockCourseCommentVoteSave();
    // Run the test
    final CourseCommentVote result = courseCommentServiceUnderTest
      .createCourseCommentVote(courseComment);

    // Verify the results
    assertThat(result).isEqualTo(null);
  }

  @Test
  void updateComment_UpdateSuccess_StatusOK() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
    final CommentAnswerDTO commentAnswerDTO = new CommentAnswerDTO("comment", "answer", "status");
    mockFindByCourseCommentId();
    mockCourseCommentSave();
    // Run the test
    final ResponseEntity result = courseCommentServiceUnderTest
      .updateComment(commentAnswerDTO, 0L, "edit");

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getFeedbackRoomCourseComments_CourseCommentsExist_CommentDTOList() {
    // Setup
    doNothing().when(mockCourseService).isUserAllowedToAccessCourse(0L);
    when(mockCourseCommentRepository.findAllByFeedbackRoomIdAndCourseIdAndIsDeletedFalse(
      0L, 0L
    )).thenReturn(List.of(ExpectedResults.getCourseComment()));
    List<CommentDTO> expectedResult = new ArrayList<>();
    expectedResult.add(
      new CommentDTO(0L, 0L, new UserDTO("name", "surname"), "comment", "status", "answer",
        new VoteDTO(0L, 0L, 0)));
    // Run the test
    final List<CommentDTO> result = courseCommentServiceUnderTest
      .getFeedbackRoomCourseComments(0L, 0L);

    // Verify the results
      assertThat(result).isEqualTo(expectedResult);
  }

  void mockFindByCourseCommentId() {
    when(mockCourseCommentRepository.findById(anyLong())).thenReturn(Optional.of(ExpectedResults.getCourseComment()));
  }

  void mockFindAllCourseComment() {
    when(mockCourseCommentRepository.findAll())
      .thenReturn(List.of(ExpectedResults.getCourseComment()));
  }

  void mockFeedbackRoom() {
    when(mockFeedbackRoomService.getFeedbackRoomById(0L))
      .thenReturn(ExpectedResults.getFeedbackRoom());
  }

  void mockCourse() {
    when(mockCourseService.getCourseById(0L)).thenReturn(ExpectedResults.getCourse());
  }

  void mockCourseCommentSave() {
    when(mockCourseCommentRepository.save(ExpectedResults.getCourseComment()))
      .thenReturn(ExpectedResults.getCourseComment());
  }

  void mockCourseCommentVoteSave() {
    when(mockCourseCommentVoteRepository.save(ExpectedResults.getCourseCommentVote()))
      .thenReturn(ExpectedResults.getCourseCommentVote());
  }

}
