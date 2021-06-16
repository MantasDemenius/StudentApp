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
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.model.User;
import com.app.ktu.model.UserComment;
import com.app.ktu.model.UserCommentVote;
import com.app.ktu.repository.UserCommentRepository;
import com.app.ktu.repository.UserCommentVoteRepository;
import com.app.ktu.security.AuthorizedUser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserCommentService {

  private UserCommentRepository userCommentRepository;
  private UserCommentVoteRepository userCommentVoteRepository;
  private FeedbackRoomService feedbackRoomService;
  private UserService userService;

  @Autowired
  public void setUserCommentRepository(UserCommentRepository userCommentRepository) {
    this.userCommentRepository = userCommentRepository;
  }

  @Autowired
  public void setUserCommentVoteRepository(
    UserCommentVoteRepository userCommentVoteRepository) {
    this.userCommentVoteRepository = userCommentVoteRepository;
  }

  @Autowired
  public void setFeedbackRoomService(FeedbackRoomService feedbackRoomService) {
    this.feedbackRoomService = feedbackRoomService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }


  public UserComment getUserCommentById(long UserCommentId) {
    return userCommentRepository.findById(UserCommentId)
      .orElseThrow(
        () -> new RecordNotFoundException(
          "Atsiliepimas su " + UserCommentId + " id buvo nerastas"));
  }

  public UserCommentVote getUserCommentVoteById(long userCommentId,
    long userCommentVoteId) {
    return getUserCommentById(userCommentId).getUserCommentVotes().stream()
      .filter(vote -> vote.getId() == userCommentVoteId).findFirst().orElseThrow(
        () -> new RecordNotFoundException(
          "Vertinimas su " + userCommentVoteId + " id buvo nerastas"));
  }

  public List<UserComment> getUserComments() {
    return userCommentRepository.findAll();
  }

  @Transactional
  public ResponseEntity createUserComment(long feedbackRoomId,
    long userId, CommentCreateDTO commentCreateDTO) {
    User user = AuthorizedUser.getAuthorizedUser();
    FeedbackRoom feedbackRoom = feedbackRoomService.getFeedbackRoomById(feedbackRoomId);
    User subject = userService.getUserById(userId);
    UserComment userComment = new UserComment();

    userComment.setUserAsker(user);
    userComment.setFeedbackRoom(feedbackRoom);
    userComment.setUserSubject(subject);
    userComment.setComment(commentCreateDTO.getComment());
    userComment.setStatus("Neatsakytas");

    userCommentRepository.save(userComment);
    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/userComments", userComment.getId()),
      HttpStatus.CREATED);
  }

  public ResponseEntity deleteUserCommentById(long userCommentId) {
    try {
      userCommentRepository.deleteById(userCommentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new BadRequestException("Komentaras negalėjo būti ištrintas");
    }
  }

  public ResponseEntity changeVote(VotePatchDTO votePatchDTO, long userCommentId, long voteId) {
    UserCommentVote userCommentVote;
    UserComment userComment = getUserCommentById(userCommentId);
    if (voteId == 0) {
      userCommentVote = createUserCommentVote(userComment);
    } else {
      userCommentVote = getUserCommentVoteById(userCommentId, voteId);
    }
    userCommentVote.setVote(votePatchDTO.getVote());

    userCommentVoteRepository.save(userCommentVote);
    return new ResponseEntity(
      LocationHeader.getLocationHeaders("/userCommentVotes", userCommentVote.getId()),
      HttpStatus.CREATED);
  }

  public UserCommentVote createUserCommentVote(UserComment userComment) {
    UserCommentVote userCommentVote = new UserCommentVote();

    User user = AuthorizedUser.getAuthorizedUser();

    userCommentVote.setUser(user);
    userCommentVote.setUserComment(userComment);
    userCommentVote.setVote(0);

    return userCommentVoteRepository.save(userCommentVote);
  }


  public ResponseEntity updateComment(CommentAnswerDTO commentAnswerDTO, long userCommentId,
    String type) {
    UserComment userComment = getUserCommentById(userCommentId);
    User user = AuthorizedUser.getAuthorizedUser();

    if (type.equals("edit")) {
      if (commentAnswerDTO.getComment() == null) {
        throw new BadRequestException("Atsiliepimas nepateiktas");
      }
      userComment.setComment(commentAnswerDTO.getComment());
    }
    userComment.setStatus(commentAnswerDTO.getStatus());
    userComment.setAnswer(commentAnswerDTO.getAnswer());
    if (type.equals("answer")) {
      userComment.setUserAnswerer(user);
    }
    userCommentRepository.save(userComment);
    return new ResponseEntity(HttpStatus.OK);
  }

  public List<CommentDTO> getFeedbackRoomUserComments(long feedbackRoomId, long subjectId) {
    long userId = AuthorizedUser.getAuthorizedUserId();
    List<UserComment> userComments = userCommentRepository
      .findAllByFeedbackRoomIdAndUserSubjectIdAndIsDeletedFalse(feedbackRoomId, subjectId);

    List<CommentDTO> commentDTOS = new ArrayList<>();
    userComments.forEach(userComment -> {
      CommentDTO commentDTO = new CommentDTO();
      User answerer = userComment.getUserAnswerer();

      commentDTO.setId(userComment.getId());
      if (answerer != null) {
        commentDTO.setAnswerer(new UserDTO(answerer.getName(), answerer.getSurname()));
      }
      commentDTO.setCreatorId(userComment.getUserAsker().getId());
      commentDTO.setComment(userComment.getComment());
      commentDTO.setStatus(userComment.getStatus());
      commentDTO.setAnswer(userComment.getAnswer());
      commentDTO.setVotes(countVotesAndGetUserVote(userComment.getUserCommentVotes(), userId));

      if (commentDTO.getVotes().getUserVote() == -2) {
        UserCommentVote userCommentVote = createUserCommentVote(userComment);
        commentDTO.getVotes().setId(userCommentVote.getId());
        commentDTO.getVotes().setUserVote(0);
      }
      commentDTOS.add(commentDTO);
    });

    commentDTOS.sort(Collections.reverseOrder());
    return commentDTOS;
  }

  public VoteDTO countVotesAndGetUserVote(List<UserCommentVote> votes, long userId) {
    VoteDTO voteDTO = new VoteDTO();
    long voteCount = 0;
    for (UserCommentVote vote : votes) {
      voteCount += vote.getVote();
      if (vote.getUser().getId() == userId) {
        voteDTO.setId(vote.getId());
        voteDTO.setUserVote(vote.getVote());
      }
    }
    voteDTO.setVotes(voteCount);

    return voteDTO;

  }
}
