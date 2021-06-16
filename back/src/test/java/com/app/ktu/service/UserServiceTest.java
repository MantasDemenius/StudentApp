package com.app.ktu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.dto.user.UserMeDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.CourseCommentVote;
import com.app.ktu.model.Email;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.Permission;
import com.app.ktu.model.Role;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.model.User;
import com.app.ktu.model.UserComment;
import com.app.ktu.model.UserCommentVote;
import com.app.ktu.model.UserCourseResponsibility;
import com.app.ktu.repository.CourseCommentVoteRepository;
import com.app.ktu.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.swing.text.html.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UserServiceTest {

    @InjectMocks
    private UserService userServiceUnderTest;
    @Mock
    private CourseService mockCourseService;
    @Mock
    private UserRepository mockUserRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService();
        closeable = MockitoAnnotations.openMocks(this);
        userServiceUnderTest = new UserService();
        userServiceUnderTest.setUserRepository(mockUserRepository);
        userServiceUnderTest.setCourseService(mockCourseService);
    }

    @Test
    void getUsers_UsersExists_AllUsers() {
        // Setup
        mockFindAllUser();
        User expectedResult = getExpectedUser();
        // Run the test
        final List<User> result = userServiceUnderTest.getUsers();

        // Verify the results
        assertThat(result).isEqualTo(List.of(expectedResult));
    }

    @Test
    void getUserByUsername_UserExists_User() {
        // Setup
        User expectedResult = getExpectedUser();
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.of(expectedResult));
        // Run the test
        final User result = userServiceUnderTest.getUserByUsername("username");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void getUserById_UserExists_User() {
        // Setup
        mockFindUserById();
        User expectedResult = getExpectedUser();
        // Run the test
        final User result = userServiceUnderTest.getUserById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void getAuthority_HasAuthorities_Authorities() {
        // Setup
        final User user = getExpectedUser();
        Set<GrantedAuthority> expectedResult = new HashSet<>();
        expectedResult.add(new SimpleGrantedAuthority("name"));
        expectedResult.add(new SimpleGrantedAuthority("ROLE_name"));
        // Run the test
        final Set<GrantedAuthority> result = userServiceUnderTest.getAuthority(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }


    void mockFindAllUser() {
        User expectedResult = getExpectedUser();
        when(mockUserRepository.findAll()).thenReturn(List.of(expectedResult));
    }

    void mockFindUserById() {
        User expectedResult = getExpectedUser();
        when(mockUserRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
    }

    User getExpectedUser(){
        Set<Role> roles = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(0L, "name"));
        roles.add(new Role(0L, "name", permissions));
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
        return new User(0L, null, null, null, null, null, null, null, roles, courses, seatUsages, emails,
          userCourseResponsibilities,
          courseCommentAnswers, courseCommentAskers, courseCommentVotes, userCommentAnswers,
          userCommentAskers, userCommentSubjects, userCommentVotes);
    }
}
