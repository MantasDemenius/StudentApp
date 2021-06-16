package com.app.ktu.repository;

import com.app.ktu.model.CourseUsage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUsageRepository extends JpaRepository<CourseUsage, Long> {

  @Query(value = "select \n"
    + "\tcu.*\n"
    + "from course_usage cu\n"
    + "inner join user_course_responsibility ucr\n"
    + "\ton cu.course_id = ucr.course_id \n"
    + "\tand ucr.user_id = :userId\n"
    + "where cu.end_date >= now()\n"
    + "order by cu.start_date \n"
    + "fetch first :quantity rows only",
    nativeQuery = true)
  List<CourseUsage> findAllUserCoursesFromNow(@Param(value = "userId") long userId,
    @Param(value = "quantity") long quantity);

}
