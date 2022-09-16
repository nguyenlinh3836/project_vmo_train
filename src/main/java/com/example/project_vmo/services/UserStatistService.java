package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.UserStatist;
import java.util.List;

public interface UserStatistService {
  void createStatic(String usernameOrEmail);
  List<UserStatist> getAllStatic(int pageNo, int pageSize);
}
