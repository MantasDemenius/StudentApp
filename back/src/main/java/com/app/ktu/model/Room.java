package com.app.ktu.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "room", schema = "public")
public class Room {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @Column(name = "address", length = 40)
  @EqualsAndHashCode.Include
  private String address;

  @Column(name = "building", length = 40)
  @EqualsAndHashCode.Include
  private String building;

  @Column(name = "room_number", length = 40)
  @EqualsAndHashCode.Include
  private String roomNumber;

  @Column(name = "type", length = 40)
  @EqualsAndHashCode.Include
  private String type;

  @OneToMany(mappedBy = "room")
  private List<Seat> seats = new ArrayList<Seat>();

  @OneToMany(mappedBy = "room")
  private List<CourseUsage> courseUsages = new ArrayList<CourseUsage>();

}
