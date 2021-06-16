package com.app.ktu.service;

import static helper.MockServlet.mockServletContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ktu.dto.feedbackRoom.FeedbackRoomStagesDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.repository.FeedbackRoomRepository;
import com.app.ktu.repository.StageRepository;
import helper.ExpectedResults;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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

class FeedbackRoomServiceTest {

    @InjectMocks
    private FeedbackRoomService feedbackRoomServiceUnderTest;
    @Mock
    private StageRepository stageRepository;
    @Mock
    private FeedbackRoomRepository feedbackRoomRepository;
    @Mock
    private UserService userService;
    @Mock
    private CourseService courseService;
    private AutoCloseable closeable;
    @BeforeEach
    void setUp() {

        closeable = MockitoAnnotations.openMocks(this);
        feedbackRoomServiceUnderTest = new FeedbackRoomService();
        feedbackRoomServiceUnderTest.setStageRepository(stageRepository);
        feedbackRoomServiceUnderTest.setCourseService(courseService);
        feedbackRoomServiceUnderTest.setFeedbackRoomRepository(feedbackRoomRepository);
        feedbackRoomServiceUnderTest.setUserService(userService);
        SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(ExpectedResults.getPrincipal(), "password"));
    }

    @Test
    void getFeedbackRoomById_FeedbackRoomExists_FeedbackRoom() {
        // Setup
        mockFindByFeedbackRoomId();
        FeedbackRoom expectedResult = ExpectedResults.getFeedbackRoom();
        // Run the test
        final FeedbackRoom result = feedbackRoomServiceUnderTest.getFeedbackRoomById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void getFeedbackRooms_FeedbackRoomsExist_FeedbackRoomList() {
        // Setup
        mockFindAllFeedbackRooms();
        FeedbackRoom expectedResult = ExpectedResults.getFeedbackRoom();
        // Run the test
        final List<FeedbackRoom> result = feedbackRoomServiceUnderTest.getFeedbackRooms();

        // Verify the results
        assertThat(result).isEqualTo(List.of(expectedResult));
    }

    @Test
    void createFeedbackRoom_FeedbackRoomCreated_StatusCreatedWithLocation() {
        // Setup
        HttpHeaders responseHeaders = new HttpHeaders();
        mockServletContext();
        URI location = ServletUriComponentsBuilder.
          fromCurrentContextPath().
          path("feedbackRooms/0")
          .build()
          .toUri();
        responseHeaders.setLocation(location);
        ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

        final FeedbackRoomStagesDTO feedbackRoomStagesDTO = ExpectedResults.getFeedbackRoomStagesDTO();
        Course course = ExpectedResults.getCourse();
        when(feedbackRoomRepository.checkInBetween(feedbackRoomStagesDTO.getStartDate(), feedbackRoomStagesDTO.getEndDate())).thenReturn(false);
        when(courseService.getCoursesUsedInDateRange(
          feedbackRoomStagesDTO.getStartDate(),
          feedbackRoomStagesDTO.getEndDate())).thenReturn(List.of(course));
        when(userService.getAllUsersByCourses(List.of(course))).thenReturn(List.of(ExpectedResults.getUser()));
        mockStagesSaveAll();
        // Run the test
        final ResponseEntity result = feedbackRoomServiceUnderTest.createFeedbackRoom(feedbackRoomStagesDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void deleteFeedbackRoomById_DeleteSuccess_StatusOK() {
        // Setup
        ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);
        // Run the test
        final ResponseEntity result = feedbackRoomServiceUnderTest.deleteFeedbackRoomById(0L);

        // Verify the results
        verify(feedbackRoomRepository).deleteById(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateFeedbackRoom() {
        // Setup
        HttpHeaders responseHeaders = new HttpHeaders();
        mockServletContext();
        URI location = ServletUriComponentsBuilder.
          fromCurrentContextPath().
          path("feedbackRooms/0")
          .build()
          .toUri();
        responseHeaders.setLocation(location);
        ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

        final FeedbackRoomStagesDTO feedbackRoomStagesDTO = ExpectedResults.getFeedbackRoomStagesDTO();
        mockFeedbackRoomSave();
        mockFindByFeedbackRoomId();
//        // Run the test
        final ResponseEntity result = feedbackRoomServiceUnderTest.updateFeedbackRoom(feedbackRoomStagesDTO, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    void mockFindByFeedbackRoomId() {
        when(feedbackRoomRepository.findById(anyLong())).thenReturn(Optional.of(ExpectedResults.getFeedbackRoom()));
    }
    void mockFindAllFeedbackRooms() {
        when(feedbackRoomRepository.findAll())
          .thenReturn(List.of(ExpectedResults.getFeedbackRoom()));
    }

    void mockFeedbackRoomSave(){
        when(feedbackRoomRepository.save(ExpectedResults.getFeedbackRoom()))
          .thenReturn(ExpectedResults.getFeedbackRoom());
    }

    void mockStagesSaveAll(){
        when(stageRepository.saveAll(List.of(ExpectedResults.getStage())))
          .thenReturn(List.of(ExpectedResults.getStage()));
    }
}
