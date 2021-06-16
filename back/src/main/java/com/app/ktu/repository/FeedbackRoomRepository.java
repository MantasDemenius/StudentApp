package com.app.ktu.repository;

import com.app.ktu.model.FeedbackRoom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRoomRepository extends JpaRepository<FeedbackRoom, Long> {

  @Query(value = "select \n"
    + "\tcase when count(*) > 0 then 'true' else 'false' end \"value\" \n"
    + "from feedback_room fr \n"
    + "where (fr.start_date < to_date(:startDate, 'YYYY-MM-DD 24H:mi:SS') and fr.end_date > to_date(:startDate, 'YYYY-MM-DD 24H:mi:SS'))\n"
    + "\tor fr.start_date > to_date(:startDate, 'YYYY-MM-DD 24H:mi:SS') and fr.start_date < to_date(:endDate, 'YYYY-MM-DD 24H:mi:SS')",
    nativeQuery = true)
  boolean checkInBetween(@Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate);

  Optional<FeedbackRoom> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate,
    LocalDateTime endDate);

  List<FeedbackRoom> findAllByOrderByStartDateDesc();
}
