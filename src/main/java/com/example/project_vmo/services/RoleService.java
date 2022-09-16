package com.example.project_vmo.services;

import com.example.project_vmo.models.request.RoleDto;

public interface RoleService {

  RoleDto createRole(RoleDto roleDto);

  RoleDto getRoleById(int id);

  RoleDto updateRole(RoleDto roleDto, int id);
}
