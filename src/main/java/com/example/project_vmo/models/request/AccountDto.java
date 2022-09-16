package com.example.project_vmo.models.request;


import com.example.project_vmo.commons.filters.ValidPassword;
import com.sun.istack.NotNull;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  @NotNull
  private String username;
  @NotNull
  @Email
  private String email;
  @ValidPassword
  private String password;
  private List<RoleDto> roles;
  private String fullName;
  @Min(value = 18, message = "Age should not be less than 18")
  @Max(value = 150, message = "Age should not be greater than 150")
  private int age;
  private Date create_at;
}
