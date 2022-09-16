package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.repositories.RoleRepo;
import com.example.project_vmo.services.RoleService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
@Autowired
private RoleRepo roleRepo;
  @Override
  public RoleDto createRole(RoleDto roleDto) {
    Role role = MapperUtil.map(roleDto, Role.class);
    return MapperUtil.map(roleRepo.save(role), RoleDto.class);
  }

  @Override
  public RoleDto getRoleById(int id) {
    Role role = roleRepo.findByRoleId(id);
    return MapperUtil.map(role, RoleDto.class);
  }

  @Override
  public RoleDto updateRole(RoleDto roleDto, int id) {
    Role role = MapperUtil.map(roleDto, Role.class);
    role.setRoleId(id);
    return MapperUtil.map(roleRepo.save(role),RoleDto.class);
  }
}
