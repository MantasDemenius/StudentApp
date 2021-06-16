package com.app.ktu.dto.comment;

import com.app.ktu.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Comparable<CommentDTO> {

  private long id;

  private long creatorId;

  private UserDTO answerer;

  private String comment;

  private String status;

  private String answer;

  private VoteDTO votes;

  @Override
  public int compareTo(CommentDTO o) {
    Integer original = this.votes == null ? null : Math.toIntExact(this.votes.getVotes());
    Integer comparable = o.votes == null ? null : Math.toIntExact(o.votes.getVotes());
    return original.compareTo(comparable);

  }
}
