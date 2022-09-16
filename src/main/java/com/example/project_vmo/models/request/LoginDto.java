package com.example.project_vmo.models.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDto {
  @NotNull
  private String usernameOrEmail;
  @NotNull
  private String password;
}
