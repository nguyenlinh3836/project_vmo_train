package com.example.project_vmo.repositories;


import com.example.project_vmo.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

  Role findByRoleId(int id);
}
