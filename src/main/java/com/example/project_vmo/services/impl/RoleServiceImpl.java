package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.commons.exception.ResourceAlreadyExistsException;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.repositories.RoleRepo;
import com.example.project_vmo.services.RoleService;
import java.time.LocalDateTime;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
@Autowired
private RoleRepo roleRepo;
  @Override
  public RoleDto createRole(RoleDto roleDto) {
    Role role = MapperUtil.map(roleDto, Role.class);
    if  ( roleRepo.findByRoleName(role.getRoleName()) != null){
      throw new ResourceAlreadyExistsException("Role name already exist !");
    } else {
      return MapperUtil.map(roleRepo.save(role), RoleDto.class);
    }
  }

  @Override
  public RoleDto getRoleById(int id) {
    Role role = roleRepo.findByRoleId(id);
    return MapperUtil.map(role, RoleDto.class);
  }

  @Override
  public RoleDto updateRole(RoleDto roleDto, int id) {
    Role role = MapperUtil.map(roleDto, Role.class);
    if  ( roleRepo.findByRoleName(role.getRoleName()) != null){
      throw new ResourceAlreadyExistsException("Role name already exist !");
    } else {
      role.setRoleId(id);
      role.setRoleName(role.getRoleName());
      role.setCreateAt(new Date());
      role.setUpdatedAt(new Date());
      return MapperUtil.map(roleRepo.save(role), RoleDto.class);
    }
  }

  @Override
  public void deleteRole(int id) {
    Role role = roleRepo.findByRoleId(id);
    roleRepo.delete(role);
  }
}
