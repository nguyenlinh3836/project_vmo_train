package com.example.project_vmo.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
  @Id
  private int accountId;
  @JsonProperty(value = "username")
  private String username;

}
