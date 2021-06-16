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
import org.hibernate.annotations.Where;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update seat set is_deleted = true where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted <> true")
@Table(name = "seat", schema = "public")
public class Seat {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", length = 40)
  private String name;

  @Column(name = "code", length = 40)
  private String code;

  @Column(name = "is_locked")
  private Boolean isLocked;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "room_id")
  @JsonIgnore
  private Room room;

  @OneToMany(mappedBy = "seat")
  private List<SeatUsage> seatUsages = new ArrayList<SeatUsage>();

}
