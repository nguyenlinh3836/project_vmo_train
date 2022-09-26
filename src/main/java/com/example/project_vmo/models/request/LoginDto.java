package com.example.project_vmo.models.request;


import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
  @NotNull(message = "Please input username or email")
  private String usernameOrEmail;
  @NotNull(message = "Please input password")
  private String password;
}
