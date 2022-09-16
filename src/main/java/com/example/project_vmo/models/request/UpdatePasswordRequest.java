package com.example.project_vmo.models.request;

import com.example.project_vmo.commons.filters.PasswordValueMatch;
import com.example.project_vmo.commons.filters.ValidPassword;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@PasswordValueMatch.List({
    @PasswordValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match!")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

  @ValidPassword
  @NonNull
  @NotBlank(message = "New password is mandatory")
  private String password;


  @ValidPassword
  @NonNull
  @NotBlank(message = "Confirm Password is mandatory")
  private String confirmPassword;
}
