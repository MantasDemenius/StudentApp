package com.app.ktu.service;

import com.app.ktu.common.LocationHeader;
import com.app.ktu.dto.comment.CommentAnswerDTO;
import com.app.ktu.dto.comment.CommentCreateDTO;
import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VoteDTO;
import com.app.ktu.dto.comment.VotePatchDTO;
import com.app.ktu.dto.user.UserDTO;
import com.app.ktu.exception.BadRequestException;
import com.app.ktu.exception.RecordNotFoundException;
import com.app.ktu.model.Course;
import com.app.ktu.model.CourseComment;
import com.app.ktu.model.CourseCommentVote;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.User;
import com.app.ktu.repository.CourseCommentRepository;
import com.app.ktu.repository.CourseCommentVoteRepository;
import com.app.ktu.security.AuthorizedUser;
import com.app.ktu.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CourseCommentService {

  private FeedbackRoomService feedbackRoomService;
  private CourseService courseService;
  private CourseCommentRepository courseCommentRepository;
  private CourseCommentVoteRepository courseCommentVoteRepository;

  @Autowired
  public void setCourseCommentRepository(CourseCommentRepository courseCommentRepository) {
    this.courseCommentRepository = courseCommentRepository;
  }

  @Autowired
  public void setCourseCommentVoteRepository(
    CourseCommentVoteRepository courseCommentVoteRepository) {
    this.courseCommentVoteRepository = courseCommentVoteRepository;
  }

  @Autowired
  public void setFeedbackRoomService(FeedbackRoomService feedbackRoomService) {
    this.feedbackRoomService = feedbackRoomService;
  }

  @Autowired
  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }


  public CourseComment getCourseCommentById(long CourseCommentId) {
    return courseCommentRepository.findById(CourseCommentId)
      .orElseThrow(
        () -> new RecordNotFoundException(
          "Komentaras su " + CourseCommentId + " id buvo nerastas"));
  }

  public CourseCommentVote getCourseCommentVoteById(long courseCommentId,
    long courseCommentVoteId) {
    return getCourseCommentById(courseCommentId).getVotes().stream()
      .filter(vote -> vote.getId() == courseCommentVoteId).findFirst().orElseThrow(
        () -> new RecordNotFoundException(
          "Vertinimas su " + courseCommentVoteId + " id buvo nerastas"));
  }

  public List<CourseComment> getCourseComments() {
    return courseCommentRepository.findAll();
  }

  @Transactional
  public ResponseEntity createCourseComment(long feedbackRoomId,
    long courseId, CommentCreateDTO commentCreateDTO) {
    User user = AuthorizedUser.getAuthorizedUser();
    FeedbackRoom feedbackRoom = feedbackRoomService.getFeedbackRoomById(feedbackRoomId);
    Course course = courseService.getCourseById(courseId);
    CourseComment courseComment = new CourseComment();

    courseComment.setAsker(user);
    courseComment.setFeedbackRoom(feedbackRoom);
    courseComment.setCourse(course);
    courseComment.setComment(commentCreateDTO.getComment());
    courseComment.setStatus("Neatsakytas");

    courseCommentRepository.save(courseComment);
    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/courseComments", courseComment.getId()),
      HttpStatus.CREATED);
  }

  public ResponseEntity deleteCourseCommentById(long courseCommentId) {
    isUserAllowedToActOnComment(courseCommentId);
    try {
      courseCommentRepository.deleteById(courseCommentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new BadRequestException("Komentaras negalėjo būti ištrintas");
    }
  }

  public ResponseEntity changeVote(VotePatchDTO votePatchDTO, long courseCommentId, long voteId) {
    CourseCommentVote courseCommentVote;
    CourseComment courseComment = getCourseCommentById(courseCommentId);
    if (voteId == 0) {
      courseCommentVote = createCourseCommentVote(courseComment);
    } else {
      courseCommentVote = getCourseCommentVoteById(courseCommentId, voteId);
    }
    courseCommentVote.setVote(votePatchDTO.getVote());

    courseCommentVoteRepository.save(courseCommentVote);
    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/courseCommentVotes", courseCommentVote.getId()),
      HttpStatus.CREATED);
  }

  public CourseCommentVote createCourseCommentVote(CourseComment courseComment) {
    CourseCommentVote courseCommentVote = new CourseCommentVote();

    User user = AuthorizedUser.getAuthorizedUser();

    courseCommentVote.setUser(user);
    courseCommentVote.setCourseComment(courseComment);
    courseCommentVote.setVote(0);

    return courseCommentVoteRepository.save(courseCommentVote);
  }


  public ResponseEntity updateComment(CommentAnswerDTO commentAnswerDTO, long courseCommentId,
    String type) {
    CourseComment courseComment = getCourseCommentById(courseCommentId);
    User user = AuthorizedUser.getAuthorizedUser();

    if (type.equals("edit")) {
      if (commentAnswerDTO.getComment() == null) {
        throw new BadRequestException("Atsiliepimas nepateiktas");
      }
      courseComment.setComment(commentAnswerDTO.getComment());
    }
    courseComment.setStatus(commentAnswerDTO.getStatus());
    courseComment.setAnswer(commentAnswerDTO.getAnswer());
    if (type.equals("answer")) {
      courseComment.setAnswerer(user);
    }
    courseCommentRepository.save(courseComment);
    return new ResponseEntity(HttpStatus.OK);
  }

  public List<CommentDTO> getFeedbackRoomCourseComments(long feedbackRoomId, long courseId) {
    courseService.isUserAllowedToAccessCourse(courseId);
    User user = AuthorizedUser.getAuthorizedUser();
    List<CourseComment> courseComments = courseCommentRepository
      .findAllByFeedbackRoomIdAndCourseIdAndIsDeletedFalse(feedbackRoomId, courseId);

    List<CommentDTO> commentDTOS = new ArrayList<>();
    courseComments.forEach(courseComment -> {
      Optional<VoteDTO> voteDTO = countVotesAndGetUserVote(courseComment.getVotes(), user);
      if (voteDTO.isEmpty()) {
        return;
      }
      CommentDTO commentDTO = new CommentDTO();
      User answerer = courseComment.getAnswerer();

      commentDTO.setId(courseComment.getId());
      if (answerer != null) {
        commentDTO.setAnswerer(new UserDTO(answerer.getName(), answerer.getSurname()));
      }
      commentDTO.setCreatorId(courseComment.getAsker().getId());
      commentDTO.setComment(courseComment.getComment());
      commentDTO.setStatus(courseComment.getStatus());
      commentDTO.setAnswer(courseComment.getAnswer());
      voteDTO.ifPresent(commentDTO::setVotes);
      if (commentDTO.getVotes().getUserVote() == -2) {
        CourseCommentVote courseCommentVote = createCourseCommentVote(courseComment);
        commentDTO.getVotes().setId(courseCommentVote.getId());
        commentDTO.getVotes().setUserVote(0);
      }

      commentDTOS.add(commentDTO);
    });

    commentDTOS.sort(Collections.reverseOrder());
    return commentDTOS;
  }

  public Optional<VoteDTO> countVotesAndGetUserVote(List<CourseCommentVote> votes, User user) {
    VoteDTO voteDTO = new VoteDTO();
    long voteCount = 0;
    for (CourseCommentVote vote : votes) {
      voteCount += vote.getVote();
      if (vote.getUser().getId() == user.getId()) {
        voteDTO.setId(vote.getId());
        voteDTO.setUserVote(vote.getVote());
      }
    }
    Principal principal = AuthorizedUser.getAuthorizedPrincipal();
    boolean isAdmin = principal.getAuthorities().stream()
      .anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN"));
    if (voteCount <= -20 && !isAdmin) {
      return Optional.empty();
    }
    voteDTO.setVotes(voteCount);

    return Optional.of(voteDTO);
  }

  public void isUserAllowedToActOnComment(long courseCommentId) {
    long userId = AuthorizedUser.getAuthorizedUserId();
    if (!courseCommentRepository.isUserAllowedToActOnComment(userId, courseCommentId)) {
      throw new AccessDeniedException("Naudotojas neturi teisių prieiti prie šio atsiliepimo");
    }
  }
}
