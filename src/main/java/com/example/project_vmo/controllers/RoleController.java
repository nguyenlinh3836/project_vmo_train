package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/role")
public class RoleController {
  @Autowired
  private RoleService roleService;
  @PostMapping
  public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto){
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.createRole(roleDto));
  }

}
