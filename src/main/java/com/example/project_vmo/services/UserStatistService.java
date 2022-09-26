package com.example.project_vmo.services;

import com.example.project_vmo.models.response.UserStatistResponse;
import com.example.project_vmo.models.response.ListStatistResponse;

public interface UserStatistService {
  UserStatistResponse createStatic(String usernameOrEmail);
  ListStatistResponse getAllStatic(int pageNo, int pageSize);
}
