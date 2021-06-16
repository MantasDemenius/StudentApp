package com.app.ktu.service;

import com.app.ktu.dto.comment.SectionDTO;
import com.app.ktu.dto.user.UserMeDTO;
import com.app.ktu.exception.RecordNotFoundException;
import com.app.ktu.model.Course;
import com.app.ktu.model.Email;
import com.app.ktu.model.Role;
import com.app.ktu.model.User;
import com.app.ktu.repository.UserRepository;
import com.app.ktu.security.AuthorizedUser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;
  private CourseService courseService;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(
        String.format("User with username: \"%s\" was not found.", username)
      ));
  }

  public User getUserById(long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new RecordNotFoundException("Naudotojas neegzistuoja!"));
  }

  public Set<GrantedAuthority> getAuthority(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();

    for (Role role : user.getRoles()) {
      role.getPermissions().stream()
        .map(p -> new SimpleGrantedAuthority(p.getName()))
        .forEach(authorities::add);
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }

    return authorities;
  }

  public User getUser() {
    return userRepository.findById(AuthorizedUser.getAuthorizedUserId())
      .orElseThrow(() -> new RecordNotFoundException("Naudotojas neegzistuoja!"));
  }

  public UserMeDTO getUserMe() {
    User user = getUser();
    Set<GrantedAuthority> userAuthorities = getAuthority(user);
    Set<String> permissions = userAuthorities.stream()
      .map(GrantedAuthority::getAuthority).collect(
        Collectors.toSet());
    String emailText = "";
    Email email = user.getEmails().stream().filter(Email::getIsMain).findFirst().orElse(null);
    if (email != null) {
      emailText = email.getEmail();
    }
    return new UserMeDTO(user.getId(), user.getName(), user.getSurname(), emailText, user.getCode(), permissions);
  }

  public List<User> getAllUsersByCourses(List<Course> courses) {
    return userRepository.findAllByCoursesIn(courses);
  }

  public List<SectionDTO> getFeedbackRoomUsers(long feedbackRoomId) {
    long userId = AuthorizedUser.getAuthorizedUserId();
    List<User> users = userRepository.findAllFeedbackRoomUsers(feedbackRoomId, userId);

    List<SectionDTO> sectionDTOS = new ArrayList<>();

    users.forEach(user -> {
      SectionDTO sectionDTO = new SectionDTO();
      sectionDTO.setId(user.getId());
      sectionDTO.setTitle(user.getName() + ' ' + user.getSurname());
      long commentCount = user.getUserCommentSubjects().stream()
        .filter(userComment -> userComment.getFeedbackRoom().getId() == feedbackRoomId && !userComment.getIsDeleted()).count();
      sectionDTO.setCommentCount(commentCount);
      sectionDTOS.add(sectionDTO);
    });

    return sectionDTOS;
  }
}
