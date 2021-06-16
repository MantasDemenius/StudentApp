package com.app.ktu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update feedback_room set is_deleted = true where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted <> true")
@Table(name = "feedback_room", schema = "public")
public class FeedbackRoom {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "start_date", length = 40, nullable = false)
  private LocalDateTime startDate;

  @Column(name = "end_date", length = 40)
  private LocalDateTime endDate;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "course_feedback_room",
    joinColumns = @JoinColumn(name = "feedback_room_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_feedback_room_visibility",
    joinColumns = @JoinColumn(name = "feedback_room_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "feedbackRoom", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Stage> stages = new ArrayList<>();

  @OneToMany(mappedBy = "feedbackRoom", cascade = CascadeType.DETACH)
  @JsonIgnore
  private List<CourseComment> courseComments = new ArrayList<>();

  @OneToMany(mappedBy = "feedbackRoom", cascade = CascadeType.DETACH)
  @JsonIgnore
  private List<UserComment> userComments = new ArrayList<>();
}
