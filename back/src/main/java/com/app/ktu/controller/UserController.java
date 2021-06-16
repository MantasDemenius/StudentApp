package com.app.ktu.controller;

import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.dto.user.UserDTO;
import com.app.ktu.dto.user.UserMeDTO;
import com.app.ktu.model.User;
import com.app.ktu.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private ModelMapper modelMapper;
  private UserService userService;

  @Autowired
  public UserService setUserService(UserService userService) {
    return this.userService = userService;
  }

  @GetMapping("/users/me")
  public UserMeDTO getMe() {
    return userService.getUserMe();
  }

  @GetMapping("/feedbackRooms/{feedbackRoomId}/users")
  @PreAuthorize("hasAuthority('feedbackUsers:read')")
  public List<SectionDTO> getFeedbackRoomUsers(
    @PathVariable(name = "feedbackRoomId") long feedbackRoomId) {
    return userService.getFeedbackRoomUsers(feedbackRoomId);
  }

  private List<UserDTO> convertUserListToUserDTOList(List<User> users) {
    return users.stream().map(this::convertUserToUserDTO)
      .collect(Collectors.toList());
  }

  private UserDTO convertUserToUserDTO(User user) {
    return modelMapper.map(user, UserDTO.class);
  }

}
