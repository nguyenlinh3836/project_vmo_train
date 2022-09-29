package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @PostMapping
  public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.createRole(roleDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRole(@RequestBody RoleDto roleDto, @PathVariable("id") int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.updateRole(roleDto, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable(value = "id") int id) {
    roleService.deleteRole(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(),"Role has been delete !"));
  }

}
