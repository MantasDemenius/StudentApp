package com.app.ktu.dto.user;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDTO {

  private long id;
  private String name;
  private String surname;
  private String email;
  private String code;
  private Set<String> permissions;

}
