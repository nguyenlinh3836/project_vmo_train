package com.example.project_vmo.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceAlreadyExistsException extends RuntimeException{
  private String message;

  public ResourceAlreadyExistsException(String message){
    super();
    this.message = message;
  }
}
