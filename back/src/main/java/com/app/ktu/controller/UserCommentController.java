package com.app.ktu.controller;

import com.app.ktu.dto.comment.CommentAnswerDTO;
import com.app.ktu.dto.comment.CommentCreateDTO;
import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VotePatchDTO;
import com.app.ktu.service.UserCommentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCommentController {

  private UserCommentService userCommentService;

  @Autowired
  public UserCommentService setUserCommentService(UserCommentService userCommentService) {
    return this.userCommentService = userCommentService;
  }

  @PostMapping("/feedbackRooms/{feedbackRoomId}/users/{userId}/userComments")
  @PreAuthorize("hasAuthority('feedbackUsers:write')")
  public ResponseEntity createUserComment(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId,
    @PathVariable(value = "userId") long userId,
    @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
    return userCommentService.createUserComment(feedbackRoomId, userId, commentCreateDTO);
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}/users/{userId}/userComments")
  @PreAuthorize("hasAuthority('feedbackUsers:feedback')")
  public List<CommentDTO> getFeedbackRoomUserComments(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId,
    @PathVariable(value = "userId") long userId) {
    return userCommentService.getFeedbackRoomUserComments(feedbackRoomId, userId);
  }

  @PatchMapping("/userComments/{userCommentId}/userCommentVotes/{voteId}")
  @PreAuthorize("hasAuthority('feedbackUsers:vote')")
  public ResponseEntity changeVote(@PathVariable(value = "userCommentId") long userCommentId,
    @PathVariable(value = "voteId") long voteId,
    @Valid @RequestBody VotePatchDTO votePatchDTO) {
    return userCommentService.changeVote(votePatchDTO, userCommentId, voteId);
  }

  @DeleteMapping("/userComments/{userCommentId}")
  @PreAuthorize("hasAuthority('feedbackUsers:delete')")
  public ResponseEntity deleteUserComment(
    @PathVariable(value = "userCommentId") long userCommentId) {
    return userCommentService.deleteUserCommentById(userCommentId);
  }

  @PutMapping("/userComments/{userCommentId}")
  @PreAuthorize("(hasAuthority('feedbackUsers:update') and #type == 'edit') or (hasAuthority('feedbackUsers:answer') and #type == 'answer')")
  public ResponseEntity updateComment(@RequestBody CommentAnswerDTO commentAnswerDTO,
    @PathVariable(value = "userCommentId") long userCommentId, @RequestParam String type) {
    return userCommentService.updateComment(commentAnswerDTO, userCommentId, type);
  }
}
