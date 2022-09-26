package com.example.project_vmo.models.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetLinkRequest {
  @NotNull(message = "Please input email")
  private String email;

}
