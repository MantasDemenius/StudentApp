package com.app.ktu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update course_comment set is_deleted = true where id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "course_comment", schema = "public")
public class CourseComment {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id_answerer")
  @JsonIgnore
  private User answerer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id_asker")
  @JsonIgnore
  private User asker;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  @JsonIgnore
  private Course course;

  @OneToMany(mappedBy = "courseComment", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<CourseCommentVote> votes = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feedback_room_id")
  @JsonIgnore
  private FeedbackRoom feedbackRoom;

  @Column(name = "comment", length = 2000)
  private String comment;

  @Column(name = "status", length = 40)
  private String status;

  @Column(name = "answer", length = 2000)
  private String answer;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

}
