package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateResponse {

  private AccountDto accountDto;

  private String message;
}
