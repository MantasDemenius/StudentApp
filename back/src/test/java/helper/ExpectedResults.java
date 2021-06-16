package helper;

import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VoteDTO;
import com.app.ktu.dto.feedbackRoom.FeedbackRoomStagesDTO;
import com.app.ktu.dto.stage.StageDTO;
import com.app.ktu.dto.user.UserDTO;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.CourseCommentVote;
import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Email;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.Role;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.model.Stage;
import com.app.ktu.model.User;
import com.app.ktu.model.UserComment;
import com.app.ktu.model.UserCommentVote;
import com.app.ktu.model.UserCourseResponsibility;
import com.app.ktu.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public class ExpectedResults {

  public static User getUser() {
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

    return new User(0L, "username", "name", "surname", null, null, null, null, roles, courses,
      seatUsages,
      emails,
      userCourseResponsibilities,
      courseCommentAnswers, courseCommentAskers, courseCommentVotes, userCommentAnswers,
      userCommentAskers, userCommentSubjects, userCommentVotes);
  }

  public static Principal getPrincipal() {

    Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    return new Principal(getUser(), authorities);
  }

  public static FeedbackRoom getFeedbackRoom() {
    Set<Course> courses = new HashSet<>();
    Set<User> users = new HashSet<>();
    List<Stage> stages = new ArrayList<>();
    List<CourseComment> courseComments = new ArrayList<>();
    List<UserComment> userComments = new ArrayList<>();

    return new FeedbackRoom(0L, LocalDateTime.of(2019, 1, 1, 8, 1),
      LocalDateTime.of(2021, 1, 1, 8, 1), false, courses, users, stages, courseComments, userComments);
  }

  public static Course getCourse() {
    List<CourseUsage> usages = new ArrayList<CourseUsage>();
    List<UserCourseResponsibility> responsibilities = new ArrayList<>();
    List<CourseComment> comments = new ArrayList<>();
    Set<User> userVisibilities = new HashSet<>();

    return new Course(0L, "code", "title", "language", usages, responsibilities, comments,
      userVisibilities);
  }

  public static CourseComment getCourseComment() {
    List<CourseCommentVote> courseCommentVotes = new ArrayList<>();
    courseCommentVotes.add(new CourseCommentVote(0L, 0, new CourseComment(), new User()));
    return new CourseComment(0L,
      getUser(),
      getUser(),
      getCourse(),
      courseCommentVotes,
      getFeedbackRoom(),
      "comment", "status", "answer", false);
  }

  public static CourseCommentVote getCourseCommentVote() {
    return new CourseCommentVote(0L, 1L, getCourseComment(), getUser());
  }

  public static FeedbackRoomStagesDTO getFeedbackRoomStagesDTO() {
    List<StageDTO> stages = new ArrayList<>();
    stages.add(new StageDTO(0L, "name", LocalDateTime.of(2020, 1, 1, 8, 1),
      LocalDateTime.of(2020, 1, 2, 8, 1)));
    return new FeedbackRoomStagesDTO(0L, LocalDateTime.of(2019, 1, 1, 8, 1),
      LocalDateTime.of(2021, 1, 1, 8, 1), stages);
  }

  public static CommentDTO getCommentDTO() {
    return new CommentDTO(0L, 0L, new UserDTO("name", "surname"), "comment", "status", "answer",
      new VoteDTO(0L, 0L, 0));
  }

  public static Stage getStage() {
    return new Stage(0L, "name", LocalDateTime.of(2020, 1, 1, 8, 1),
      LocalDateTime.of(2020, 1, 2, 8, 1), getFeedbackRoom());
  }

  public static UserCommentVote getUserCommentVote() {
    return new UserCommentVote(0L, new UserComment(), getUser(), 0L);
  }

  public static UserComment getUserComment() {
    List<UserCommentVote> userCommentVotes = new ArrayList<>();
    userCommentVotes.add(getUserCommentVote());
    return new UserComment(0L,
      getUser(),
      getUser(),
      getUser(),
      userCommentVotes,
      "comment", "status", "answer", getFeedbackRoom(), false);
  }
}
