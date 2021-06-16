package com.app.ktu.repository;

import com.app.ktu.model.Seat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

  Optional<Seat> findByIdAndRoomId(long seatId, long roomId);

  int countByRoomId(long roomId);

  Optional<Seat> findByCode(String code);

  @Query(value = "select \n"
    + "\ts.*\n"
    + "from course_usage cu\n"
    + "inner join room r \n"
    + "\ton cu.room_id =r.id \n"
    + "inner join seat s \n"
    + "\ton r.id = s.room_id\n"
    + "left join seat_usage su \n"
    + "\ton s.id = su.seat_id \n"
    + "\tand su.end_date is null\n"
    + "where cu.id = :courseUsageId", nativeQuery = true)
  List<Seat> findAllSeatsWithUsagesByCourseUsageId(@Param("courseUsageId") long courseUsageId);

  List<Seat> findAllByRoomIdOrderByName(long roomId);
}
