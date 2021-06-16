package com.app.ktu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_usage", schema = "public")
public class CourseUsage {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "course_id")
  @JsonIgnore
  private Course course;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "room_id")
  @JsonIgnore
  private Room room;

  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "type_of_session", length = 40, nullable = false)
  private String typeOfSession;

  @OneToMany(mappedBy = "courseUsage")
  private List<SeatUsage> seatUsages = new ArrayList<SeatUsage>();

  public String getCourseTime() {
    return this.startDate.toLocalTime().toString() + " - " + this.endDate.toLocalTime()
      .toString();
  }

}
