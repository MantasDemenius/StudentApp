package com.app.ktu.controller;

import com.app.ktu.dto.feedbackRoom.FeedbackRoomDTO;
import com.app.ktu.dto.feedbackRoom.FeedbackRoomStagesDTO;
import com.app.ktu.model.FeedbackRoom;
import com.app.ktu.service.FeedbackRoomService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackRoomController {

  private FeedbackRoomService feedbackRoomService;
  @Autowired
  private ModelMapper modelMapper;

  public FeedbackRoomController(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public FeedbackRoomService setFeedbackRoomService(FeedbackRoomService feedbackRoomService) {
    return this.feedbackRoomService = feedbackRoomService;
  }

  @GetMapping("/feedbackRooms")
  @PreAuthorize("hasAuthority('feedbackRooms:read')")
  public List<FeedbackRoomStagesDTO> getFeedbackRooms() {
    return convertFeedbackRoomListToFeedbackRoomStagesDTOList(
      feedbackRoomService.getFeedbackRooms());
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}")
  @PreAuthorize("hasAuthority('feedbackRooms:read')")
  public FeedbackRoomDTO getFeedbackRoomById(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId) {
    return convertFeedbackRoomToFeedbackRoomDTO(
      feedbackRoomService.getFeedbackRoomById(feedbackRoomId));
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}/stages")
  @PreAuthorize("hasAuthority('feedbackRooms:stages')")
  public FeedbackRoomStagesDTO getFeedbackRoomByIdStages(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId) {
    return convertFeedbackRoomToFeedbackRoomStagesDTO(
      feedbackRoomService.getFeedbackRoomById(feedbackRoomId));
  }

  @PostMapping("/feedbackRooms")
  @PreAuthorize("hasAuthority('feedbackRooms:create')")
  public ResponseEntity createFeedbackRoom(
    @Valid @RequestBody FeedbackRoomStagesDTO feedbackRoomStagesDTO) {
    return feedbackRoomService.createFeedbackRoom(feedbackRoomStagesDTO);
  }

  @PutMapping("/feedbackRooms/{feedbackRoomId}")
  @PreAuthorize("hasAuthority('feedbackRooms:update')")
  public ResponseEntity updateFeedbackRoom(
    @Valid @RequestBody FeedbackRoomStagesDTO feedbackRoomStagesDTO,
    @PathVariable("feedbackRoomId") long feedbackRoomId) {
    return feedbackRoomService.updateFeedbackRoom(feedbackRoomStagesDTO, feedbackRoomId);
  }

  @DeleteMapping("/feedbackRooms/{feedbackRoomId}")
  @PreAuthorize("hasAuthority('feedbackRooms:delete')")
  public ResponseEntity deleteFeedbackRoom(
    @PathVariable(value = "feedbackRoomId") long feedbackRoomId) {
    return feedbackRoomService.deleteFeedbackRoomById(feedbackRoomId);
  }

  @GetMapping("/feedbackRooms/active")
  @PreAuthorize("hasAuthority('feedbackRooms:active')")
  public FeedbackRoomStagesDTO getActiveFeedbackRoom() {
    return convertFeedbackRoomToFeedbackRoomStagesDTO(feedbackRoomService.getActiveFeedbackRoom());
  }

  private List<FeedbackRoomDTO> convertFeedbackRoomListToFeedbackRoomDTOList(
    List<FeedbackRoom> feedbackRooms) {
    return feedbackRooms.stream().map(this::convertFeedbackRoomToFeedbackRoomDTO)
      .collect(Collectors.toList());
  }

  private FeedbackRoomDTO convertFeedbackRoomToFeedbackRoomDTO(FeedbackRoom feedbackRoom) {
    return modelMapper.map(feedbackRoom, FeedbackRoomDTO.class);
  }

  private List<FeedbackRoomStagesDTO> convertFeedbackRoomListToFeedbackRoomStagesDTOList(
    List<FeedbackRoom> feedbackRooms) {
    return feedbackRooms.stream().map(this::convertFeedbackRoomToFeedbackRoomStagesDTO)
      .collect(Collectors.toList());
  }

  private FeedbackRoomStagesDTO convertFeedbackRoomToFeedbackRoomStagesDTO(
    FeedbackRoom feedbackRoom) {
    return modelMapper.map(feedbackRoom, FeedbackRoomStagesDTO.class);
  }
}
