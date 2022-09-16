package com.example.project_vmo.models.request;

import com.example.project_vmo.models.entities.Account;
import java.util.Date;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStaticDto {
  @Id
  private int staticId;
  private int count;
  private Account account;
  private Date lastLogin;

}
