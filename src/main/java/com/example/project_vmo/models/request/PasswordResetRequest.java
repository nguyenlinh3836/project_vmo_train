package com.example.project_vmo.models.request;

import com.example.project_vmo.commons.filters.PasswordValueMatch;
import com.example.project_vmo.commons.filters.ValidPassword;
import lombok.Data;

@Data
@PasswordValueMatch.List({
    @PasswordValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match!")
})
public class PasswordResetRequest {
  private String email;
  @ValidPassword
  private String password;
  @ValidPassword
  private String confirmPassword;
  private String token;
}
