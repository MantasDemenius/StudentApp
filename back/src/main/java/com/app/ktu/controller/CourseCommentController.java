package com.app.ktu.controller;

import com.app.ktu.dto.comment.CommentAnswerDTO;
import com.app.ktu.dto.comment.CommentCreateDTO;
import com.app.ktu.dto.comment.CommentDTO;
import com.app.ktu.dto.comment.VotePatchDTO;
import com.app.ktu.service.CourseCommentService;
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
public class CourseCommentController {

  private CourseCommentService courseCommentService;

  @Autowired
  public CourseCommentService setCourseCommentService(CourseCommentService courseCommentService) {
    return this.courseCommentService = courseCommentService;
  }

  @PostMapping("/feedbackRooms/{feedbackRoomId}/courses/{courseId}/courseComments")
  @PreAuthorize("hasAuthority('feedbackCourses:write')")
  public ResponseEntity createCourseComment(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId,
    @PathVariable(value = "courseId") long courseId,
    @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
    return courseCommentService.createCourseComment(feedbackRoomId, courseId, commentCreateDTO);
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}/courses/{courseId}/courseComments")
  @PreAuthorize("hasAuthority('feedbackCourses:feedback')")
  public List<CommentDTO> getFeedbackRoomCourseComments(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId,
    @PathVariable(value = "courseId") long courseId) {
    return courseCommentService.getFeedbackRoomCourseComments(feedbackRoomId, courseId);
  }

  @PatchMapping("/courseComments/{courseCommentId}/courseCommentVotes/{voteId}")
  @PreAuthorize("hasAuthority('feedbackCourses:vote')")
  public ResponseEntity changeVote(@PathVariable(value = "courseCommentId") long courseCommentId,
    @PathVariable(value = "voteId") long voteId,
    @Valid @RequestBody VotePatchDTO votePatchDTO) {
    return courseCommentService.changeVote(votePatchDTO, courseCommentId, voteId);
  }

  @DeleteMapping("/courseComments/{courseCommentId}")
  @PreAuthorize("hasAuthority('feedbackCourses:delete')")
  public ResponseEntity deleteCourseComment(
    @PathVariable(value = "courseCommentId") long courseCommentId) {
    return courseCommentService.deleteCourseCommentById(courseCommentId);
  }

  @PutMapping("/courseComments/{courseCommentId}")
  @PreAuthorize("(hasAuthority('feedbackCourses:update') and #type == 'edit') or (hasAuthority('feedbackCourses:answer') and #type == 'answer')")
  public ResponseEntity updateComment(@Valid @RequestBody CommentAnswerDTO commentAnswerDTO,
    @PathVariable(value = "courseCommentId") long courseCommentId, @RequestParam String type) {
    return courseCommentService.updateComment(commentAnswerDTO, courseCommentId, type);
  }
}
