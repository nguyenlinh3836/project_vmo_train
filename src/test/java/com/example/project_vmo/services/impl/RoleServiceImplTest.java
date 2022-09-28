package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.repositories.RoleRepo;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

  @Mock
  private RoleRepo mockRoleRepo;

  @InjectMocks
  private RoleServiceImpl roleServiceImplUnderTest;

  @Test
  void testCreateRole() {
    // Setup
    final RoleDto roleDto = new RoleDto(0, "roleName");
    final RoleDto expectedResult = new RoleDto(0, "roleName");

    // Configure RoleRepo.findByRoleName(...).
    final Role role = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.findByRoleName("roleName")).thenReturn(role);

    // Configure RoleRepo.save(...).
    final Role role1 = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.save(any(Role.class))).thenReturn(role1);

    // Run the test
    final RoleDto result = roleServiceImplUnderTest.createRole(roleDto);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testCreateRole_RoleRepoFindByRoleNameReturnsNull() {
    // Setup
    final RoleDto roleDto = new RoleDto(0, "roleName");
    final RoleDto expectedResult = new RoleDto(0, "roleName");
    when(mockRoleRepo.findByRoleName("roleName")).thenReturn(null);

    // Configure RoleRepo.save(...).
    final Role role = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.save(any(Role.class))).thenReturn(role);

    // Run the test
    final RoleDto result = roleServiceImplUnderTest.createRole(roleDto);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetRoleById() {
    // Setup
    final RoleDto expectedResult = new RoleDto(0, "roleName");

    // Configure RoleRepo.findByRoleId(...).
    final Role role = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.findByRoleId(0)).thenReturn(role);

    // Run the test
    final RoleDto result = roleServiceImplUnderTest.getRoleById(0);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdateRole() {
    // Setup
    final RoleDto roleDto = new RoleDto(0, "roleName");
    final RoleDto expectedResult = new RoleDto(0, "roleName");

    // Configure RoleRepo.save(...).
    final Role role = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.save(any(Role.class))).thenReturn(role);

    // Run the test
    final RoleDto result = roleServiceImplUnderTest.updateRole(roleDto, 0);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testDeleteRole() {
    // Setup
    // Configure RoleRepo.findByRoleId(...).
    final Role role = new Role(0, "roleName",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(mockRoleRepo.findByRoleId(0)).thenReturn(role);

    // Run the test
    roleServiceImplUnderTest.deleteRole(0);

    // Verify the results
    verify(mockRoleRepo).delete(any(Role.class));
  }
}
