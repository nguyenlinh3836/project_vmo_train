package com.example.project_vmo.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
  private int code;
  private String message;


  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }

}
