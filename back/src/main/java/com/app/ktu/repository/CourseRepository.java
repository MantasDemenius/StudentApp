package com.app.ktu.repository;

import com.app.ktu.model.Course;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query(value = "select \n"
    + "\tcase when count(*) >= 1 then 'true' else 'false' end \"value\"\n"
    + "from user_course_visibility ucv \n"
    + "where user_id = :userId and ucv.course_id = :courseId", nativeQuery = true)
  boolean isUserAllowedToAccessCourse(@Param("userId") long userId,
    @Param("courseId") long courseId);

  List<Course> findAllByIdIn(List<Long> courseIds);

  @Query(value = "select \n"
    + "\tc.id,\n"
    + "\tc.code,\n"
    + "\tc.title,\n"
    + "\tc.\"language\" \n"
    + "from course c \n"
    + "inner join course_usage cu \n"
    + "\ton c.id = cu.course_id \n"
    + "\tand cu.start_date >= to_date(:startDate, 'YYYY-MM-DD HH24:MI:SS')\n"
    + "\tand cu.start_date <= to_date(:endDate, 'YYYY-MM-DD HH24:MI:SS')\n"
    + "group by c.id, c.code, c.title, c.\"language\"", nativeQuery = true)
  List<Course> findAllCoursesInDateRange(@Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate);

  @Query(value = "select \n"
    + "\tc.id,\n"
    + "\tc.code,\n"
    + "\tc.title,\n"
    + "\tc.\"language\" \n"
    + "from course_feedback_room crt \n"
    + "inner join course c \n"
    + "\ton crt.course_id = c.id \n"
    + "inner join user_course_visibility ucv \n"
    + "\ton c.id = ucv.course_id \n"
    + "\tand ucv.user_id = :userId\n"
    + "where crt.feedback_room_id = :feedbackRoomId\n"
    + "group by c.id, c.code, c.title, c.\"language\" ", nativeQuery = true)
  List<Course> findAllFeedbackRoomCourses(@Param("feedbackRoomId") long feedbackRoomId,
    @Param("userId") long userId);

  List<Course> findAllByUserVisibilitiesIdOrderByTitle(long userId);
}
