package com.example.project_vmo.models.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAccountRequest {
  @NotNull(message = "Please input fullName")
  private String fullName;
  @NotNull(message = "Please input address")
  private String address;
  @Min(value = 18, message = "Age should not be less than 18")
  @Max(value = 150, message = "Age should not be greater than 150")
  private int age;
  @NotNull(message = "Please input phone")
  private String phone;
}
