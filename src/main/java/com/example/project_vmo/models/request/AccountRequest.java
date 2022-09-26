package com.example.project_vmo.models.request;


import com.example.project_vmo.commons.filters.ValidPassword;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
  @NotNull(message = "username must input")
  private String username;
  @NotNull(message = "username must input")
  @Email(message = "email must input")
  private String email;
  @ValidPassword
  private String password;
  private List<RoleDto> roles;
  @NotEmpty(message = "fullName must input")
  private String fullName;
  @Min(value = 18, message = "Age should not be less than 18")
  @Max(value = 150, message = "Age should not be greater than 150")
  private int age;
  private Date create_at;
}
