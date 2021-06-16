package com.app.ktu.repository;

import com.app.ktu.model.CourseCommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCommentVoteRepository extends JpaRepository<CourseCommentVote, Long> {
  
  boolean existsByUserIdAndCourseCommentId(long userId, long courseCommentId);
}
