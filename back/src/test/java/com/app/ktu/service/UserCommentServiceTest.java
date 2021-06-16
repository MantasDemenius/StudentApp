package com.app.ktu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.app.ktu.dto.comment.CommentAnswerDTO;
import com.app.ktu.dto.comment.CommentCreateDTO;
import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VoteDTO;
import com.app.ktu.dto.comment.VotePatchDTO;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.UserComment;
import com.app.ktu.model.UserCommentVote;
import com.app.ktu.repository.CourseCommentRepository;
import com.app.ktu.repository.CourseCommentVoteRepository;
import com.app.ktu.repository.UserCommentRepository;
import com.app.ktu.repository.UserCommentVoteRepository;
import helper.ExpectedResults;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

class UserCommentServiceTest {

    @Mock
    private UserCommentService userCommentServiceUnderTest;
    @Mock
    private FeedbackRoomService mockFeedbackRoomService;
    @Mock
    private UserService userService;
    @Mock
    private UserCommentRepository mockUserCommentRepository;
    @Mock
    private UserCommentVoteRepository mockUserCommentVoteRepository;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        userCommentServiceUnderTest = new UserCommentService();
        closeable = MockitoAnnotations.openMocks(this);
        userCommentServiceUnderTest.setFeedbackRoomService(mockFeedbackRoomService);
        userCommentServiceUnderTest.setUserCommentRepository(mockUserCommentRepository);
        userCommentServiceUnderTest.setUserService(userService);
        userCommentServiceUnderTest.setUserCommentVoteRepository(mockUserCommentVoteRepository);
        SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(ExpectedResults.getPrincipal(), "password"));
    }

    @Test
    void testGetUserCommentById() {
        // Setup
        UserComment expectedResult = ExpectedResults.getUserComment();
        mockFindByUserCommentId();
        // Run the test
        final UserComment result = userCommentServiceUnderTest.getUserCommentById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }


    void mockFindByUserCommentId() {
        when(mockUserCommentRepository.findById(anyLong())).thenReturn(Optional.of(ExpectedResults.getUserComment()));
    }

    void mockFindAllUserComments() {
        when(mockUserCommentRepository.findAll())
          .thenReturn(List.of(ExpectedResults.getUserComment()));
    }

    void mockFeedbackRoom() {
        when(mockFeedbackRoomService.getFeedbackRoomById(0L))
          .thenReturn(ExpectedResults.getFeedbackRoom());
    }

    void mockUser() {
        when(userService.getUserById(0L)).thenReturn(ExpectedResults.getUser());
    }

    void mockCourseCommentSave() {
        when(mockUserCommentRepository.save(ExpectedResults.getUserComment()))
          .thenReturn(ExpectedResults.getUserComment());
    }

    void mockCourseCommentVoteSave() {
        when(mockUserCommentVoteRepository.save(ExpectedResults.getUserCommentVote()))
          .thenReturn(ExpectedResults.getUserCommentVote());
    }
}
