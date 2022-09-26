package com.example.project_vmo.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateResponse {
  private int code;

  private AccountResponse accountResponse;

  private String message = "Account has been create !";
}
