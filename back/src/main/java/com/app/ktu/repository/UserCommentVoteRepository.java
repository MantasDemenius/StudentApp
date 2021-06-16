package com.app.ktu.repository;

import com.app.ktu.model.UserCommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentVoteRepository extends JpaRepository<UserCommentVote, Long> {

}
