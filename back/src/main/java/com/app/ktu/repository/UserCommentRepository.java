package com.app.ktu.repository;

import com.app.ktu.model.UserComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

  List<UserComment> findAllByFeedbackRoomIdAndUserSubjectIdAndIsDeletedFalse(long feedbackRoomId, long subjectId);
}
