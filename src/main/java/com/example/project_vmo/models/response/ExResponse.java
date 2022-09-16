package com.example.project_vmo.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExResponse {
  private Object data;
  private String message;
  private boolean error = true;

  public ExResponse(Object data, String message){
    this.data = data;
    this.message = message;
  }
}
