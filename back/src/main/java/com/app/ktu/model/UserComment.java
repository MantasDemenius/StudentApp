package com.app.ktu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
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
@SQLDelete(sql = "update user_comment set is_deleted = true where id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "user_comment", schema = "public")
public class UserComment {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id_answerer")
  @JsonIgnore
  private User userAnswerer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id_asker")
  @JsonIgnore
  private User userAsker;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id_subject")
  @JsonIgnore
  private User userSubject;

  @OneToMany(mappedBy = "userComment")
  @JsonIgnore
  private List<UserCommentVote> userCommentVotes = new ArrayList<>();

  @Column(name = "comment", length = 500)
  private String comment;

  @Column(name = "status", length = 40)
  private String status;

  @Column(name = "answer", length = 500)
  private String answer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feedback_room_id")
  @JsonIgnore
  private FeedbackRoom feedbackRoom;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;
}
