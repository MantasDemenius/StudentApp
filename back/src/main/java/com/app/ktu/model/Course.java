package com.app.ktu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course", schema = "public")
public class Course {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "code", length = 40, nullable = false)
  private String code;

  @Column(name = "title", length = 40, nullable = false)
  private String title;

  @Column(name = "language", length = 40, nullable = false)
  private String language;

  @OneToMany(mappedBy = "course")
  private List<CourseUsage> usages = new ArrayList<CourseUsage>();

  @OneToMany(mappedBy = "course")
  private List<UserCourseResponsibility> responsibilities = new ArrayList<>();

  @OneToMany(mappedBy = "course")
  private List<CourseComment> comments = new ArrayList<>();

  @ManyToMany(mappedBy = "courses")
  Set<User> userVisibilities;


}
