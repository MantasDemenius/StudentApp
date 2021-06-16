package com.app.ktu.dto.comment;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsMarkDTO {

  private List<Long> ids;
}
