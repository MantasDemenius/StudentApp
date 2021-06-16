package com.app.ktu.repository;

import com.app.ktu.model.CourseComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {

  List<CourseComment> findAllByFeedbackRoomIdAndCourseIdAndIsDeletedFalse(long feedbackRoomId, long courseId);

  @Query(value = "select \n"
    + "\tcase when count(*) >= 1 then 'true' else 'false' end \"value\"\n"
    + "from course_comment cc \n"
    + "inner join user_role ur \n"
    + "\ton ur.user_id = :userId\n"
    + "where (cc.user_id_asker = :userId or cc.user_id_answerer = :userId or ur.role_id = 5) and cc.id = :courseCommentId", nativeQuery = true)
  boolean isUserAllowedToActOnComment(@Param("userId") long userId,
    @Param("courseCommentId") long courseCommentId);
}
