package com.example.project_vmo.models.response;

import java.util.Date;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistResponse {
  @Id
  private int staticId;
  private int count;
  private AccountResponse accountResponse;
  private Date lastLogin;
  private int code;

}
