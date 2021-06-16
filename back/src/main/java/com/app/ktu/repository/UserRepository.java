package com.app.ktu.repository;

import com.app.ktu.model.Course;
import com.app.ktu.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  List<User> findAllByCoursesIn(List<Course> courses);

  @Query(
    value = "select \n"
      + "\tu.id,\n"
      + "\tu.username,\n"
      + "\tu.\"name\",\n"
      + "\tu.surname,\n"
      + "\tu.\"password\",\n"
      + "\tu.picture_path,\n"
      + "\tu.code,\n"
      + "\tu.user_type \n"
      + "from course_feedback_room cfr \n"
      + "inner join user_course_responsibility ucr \n"
      + "\ton cfr.course_id = ucr.course_id \n"
      + "\tand ucr.is_head is false \n"
      + "inner join user_course_visibility ucv \n"
      + "\ton ucr.course_id = ucv.course_id \n"
      + "\tand ucv.user_id = :userId\n"
      + "inner join \"user\" u \n"
      + "\ton ucr.user_id = u.id\n"
      + "where cfr.feedback_room_id = :feedbackRoomId\n"
      + "group by u.id, u.\"name\", u.surname, u.\"password\", u.picture_path, u.code, u.user_type", nativeQuery = true
  )
  List<User> findAllFeedbackRoomUsers(@Param("feedbackRoomId") long feedbackRoomId,
    @Param("userId") long userId);


  @Query(value = "select \n"
    + "\tu.id,\n"
    + "\tu.username,\n"
    + "\tu.\"name\",\n"
    + "\tu.surname,\n"
    + "\tu.\"password\",\n"
    + "\tu.picture_path,\n"
    + "\tu.code,\n"
    + "\tu.user_type \n"
    + "from user_course_responsibility ucr \n"
    + "inner join course_usage cu \n"
    + "\ton ucr.course_id = cu.course_id \n"
    + "inner join user_course_usage ucu \n"
    + "\ton cu.id = ucu.course_usage_id \n"
    + "inner join \"user\" u \n"
    + "\ton ucu.user_id = u.id \n"
    + "where ucr.course_id = :courseId and ucr.user_id = :userId\n"
    + "group by u.id", nativeQuery = true)
  List<User> findAllCourseUsers(@Param("courseId") long courseId, @Param("userId") long userId);
}
