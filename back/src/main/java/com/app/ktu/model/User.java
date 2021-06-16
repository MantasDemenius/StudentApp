package com.app.ktu.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user", schema = "public")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @EqualsAndHashCode.Include
  @Column(name = "username", length = 40, nullable = false)
  private String username;

  @EqualsAndHashCode.Include
  @Column(name = "name", length = 40, nullable = false)
  private String name;

  @EqualsAndHashCode.Include
  @Column(name = "surname", length = 40, nullable = false)
  private String surname;

  @Column(name = "password", length = 40, nullable = false)
  private String password;

  @Column(name = "picture_path")
  private String picturePath;

  @Column(name = "code")
  @EqualsAndHashCode.Include
  private String code;

  @EqualsAndHashCode.Include
  @Column(name = "user_type")
  private String userType;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_course_visibility",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<SeatUsage> seatUsages = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Email> emails = new ArrayList<Email>();

  @OneToMany(mappedBy = "user")
  private List<UserCourseResponsibility> userCourseResponsibilities = new ArrayList<>();

  @OneToMany(mappedBy = "answerer")
  @JsonIgnore
  private List<CourseComment> courseCommentAnswers = new ArrayList<>();

  @OneToMany(mappedBy = "asker")
  @JsonIgnore
  private List<CourseComment> courseCommentAskers = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<CourseCommentVote> courseCommentVotes = new ArrayList<>();

  @OneToMany(mappedBy = "userAnswerer")
  @JsonIgnore
  private List<UserComment> userCommentAnswers = new ArrayList<>();

  @OneToMany(mappedBy = "userAsker")
  @JsonIgnore
  private List<UserComment> userCommentAskers = new ArrayList<>();

  @OneToMany(mappedBy = "userSubject")
  @JsonIgnore
  private List<UserComment> userCommentSubjects = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<UserCommentVote> userCommentVotes = new ArrayList<>();
}
