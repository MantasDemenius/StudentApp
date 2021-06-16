package com.app.ktu.repository;

import com.app.ktu.dto.seat.SeatUsageHistoryDTO;
import com.app.ktu.model.SeatUsage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatUsageRepository extends JpaRepository<SeatUsage, Long> {

  Optional<SeatUsage> findByUserIdAndEndDateIsNull(long userId);

  Optional<SeatUsage> findByIdAndEndDateIsNull(long seatUsageId);

  @Query(value = "select \n"
    + "\tsu.*\n"
    + "from seat_usage su \n"
    + "inner join course_usage cu \n"
    + "\ton su.course_usage_id = cu.id \n"
    + "inner join user_course_visibility ucv \n"
    + "\ton cu.course_id = ucv.course_id \n"
    + "\tand ucv.course_id = :courseId\n"
    + "\tand ucv.user_id = :userId\n"
    + "\torder by su.start_date desc", nativeQuery = true)
  List<SeatUsage> findAllCourseSeatUsages(@Param(value = "courseId") long courseId,
    @Param(value = "userId") long userId);

//  @Query(value = "select \n"
//    + "\tsu.course_usage_id \"courseUsage\",\n"
//    + "\tsu.seat_id \"seat\",\n"
//    + "\tsu.user_id \"user\"\n"
//    + "from seat_usage su \n"
//    + "inner join course_usage cu \n"
//    + "\ton su.course_usage_id = cu.id \n"
//    + "inner join user_course_visibility ucv \n"
//    + "\ton cu.course_id = ucv.course_id \n"
//    + "\tand ucv.course_id = :courseId\n"
//    + "\tand ucv.user_id = :userId\n"
//    + "group by \tsu.course_usage_id,\n"
//    + "\tsu.seat_id,\n"
//    + "\tsu.user_id", nativeQuery = true)
//  List<SeatUsageHistoryDTO> findAllCourseSeatUsages(@Param(value = "courseId") long courseId,
//    @Param(value = "userId") long userId);
}
