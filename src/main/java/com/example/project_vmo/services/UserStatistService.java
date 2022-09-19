package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.response.UserStatistResponse;
import java.util.List;

public interface UserStatistService {
  void createStatic(String usernameOrEmail);
  UserStatistResponse getAllStatic(int pageNo, int pageSize);
}
