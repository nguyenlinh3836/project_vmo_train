package com.example.project_vmo.models.request;


import lombok.Data;

@Data
public class UpdateAccountDto {
  private String fullName;
  private String address;
  private int age;
  private String phone;
}
