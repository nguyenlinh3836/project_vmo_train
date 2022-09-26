package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project_vmo.commons.filters.PhoneValidator;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.request.AccountRequest;
import com.example.project_vmo.models.request.RoleDto;
import com.example.project_vmo.models.request.UpdateAccountRequest;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.AccountResponse;
import com.example.project_vmo.models.response.RoleListResponse;
import com.example.project_vmo.repositories.AccountRepo;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

  @Mock
  private AccountRepo mockAccountRepo;
  @Mock
  private PasswordEncoder mockPasswordEncoder;
  @Mock
  private PhoneValidator mockPhoneValidator;

  @InjectMocks
  private AccountServiceImpl accountServiceImplUnderTest;

  @Test
  void testGetAll() {
    // Setup
    final List<AccountRequest> expectedResult = List.of(
        new AccountRequest("username", "email", "password", List.of(new RoleDto(0, "roleName")),
            "fullName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));

    // Configure AccountRepo.findAll(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final List<Account> accounts = List.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))));
    when(mockAccountRepo.findAll()).thenReturn(accounts);

    // Run the test
    final List<AccountRequest> result = accountServiceImplUnderTest.getAll();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAll_AccountRepoReturnsNoItems() {
    // Setup
    when(mockAccountRepo.findAll()).thenReturn(Collections.emptyList());

    // Run the test
    final List<AccountRequest> result = accountServiceImplUnderTest.getAll();

    // Verify the results
    assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void testCreateAccount() {
    // Setup
    final AccountRequest accountRequest = new AccountRequest("username", "email@gmail.com", "password",
        List.of(new RoleDto(0, "roleName")), "fullName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final AccountResponse expectedResult = new AccountResponse(1, "username");

    // Configure AccountRepo.findByEmail(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Optional<Account> account = Optional.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))));
    when(mockAccountRepo.findByEmail("email")).thenReturn(account);

    // Run the test
    final AccountResponse result = accountServiceImplUnderTest.createAccount(accountRequest);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(mockAccountRepo).save(any(Account.class));
  }

  @Test
  void testCreateAccount_AccountRepoFindByEmailReturnsAbsent() {
    // Setup
    final AccountRequest accountRequest = new AccountRequest("username", "email@gmail.com", "password",
        List.of(new RoleDto(1, "roleName")), "fullName", 20,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final AccountResponse expectedResult = new AccountResponse(1, "username");
    when(mockAccountRepo.findByEmail("email")).thenReturn(Optional.empty());

    // Configure AccountRepo.findByUsername(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(1);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(20);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Account account = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(1, "roleName")), userStatist, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.findByUsername("username")).thenReturn(account);

    when(mockPasswordEncoder.encode("password")).thenReturn("password");

    // Configure AccountRepo.save(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(1);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(20);
    account3.setPhone("phone");
    account3.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role1 = new Role();
    account3.setRoles(List.of(role1));
    userStatist1.setAccount(account3);
    final Account account2 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist1, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.save(any(Account.class))).thenReturn(account2);

    // Run the test
    final AccountResponse result = accountServiceImplUnderTest.createAccount(accountRequest);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(mockAccountRepo).save(any(Account.class));
  }

  @Test
  void testCreateAccount_AccountRepoFindByUsernameReturnsNull() {
    // Setup
    final AccountRequest accountRequest = new AccountRequest("username", "email", "password",
        List.of(new RoleDto(0, "roleName")), "fullName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final AccountResponse expectedResult = new AccountResponse(0, "username");

    // Configure AccountRepo.findByEmail(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Optional<Account> account = Optional.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))));
    when(mockAccountRepo.findByEmail("email")).thenReturn(account);

    when(mockAccountRepo.findByUsername("username")).thenReturn(null);
    when(mockPasswordEncoder.encode("password")).thenReturn("password");

    // Configure AccountRepo.save(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(0);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(0);
    account3.setPhone("phone");
    account3.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role1 = new Role();
    account3.setRoles(List.of(role1));
    userStatist1.setAccount(account3);
    final Account account2 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist1, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.save(any(Account.class))).thenReturn(account2);

    // Run the test
    final AccountResponse result = accountServiceImplUnderTest.createAccount(accountRequest);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(mockAccountRepo).save(any(Account.class));
  }

  @Test
  void testDeleteAccount() {
    // Setup
    // Configure AccountRepo.findByAccountId(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Account account = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.findByAccountId(0)).thenReturn(account);

    // Run the test
    accountServiceImplUnderTest.deleteAccount(0);

    // Verify the results
  }

  @Test
  void testGetAccountByRole() {
    // Setup
    final RoleListResponse expectedResult = new RoleListResponse();
    final AccountRequest accountRequest = new AccountRequest();
    accountRequest.setUsername("username");
    accountRequest.setEmail("email");
    accountRequest.setPassword("password");
    final RoleDto roleDto = new RoleDto();
    roleDto.setRoleId(0);
    roleDto.setRoleName("roleName");
    accountRequest.setRoles(List.of(roleDto));
    accountRequest.setFullName("fullName");
    accountRequest.setAge(0);
    accountRequest.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    expectedResult.setContent(List.of(accountRequest));
    expectedResult.setPageNo(0);
    expectedResult.setPageSize(5);
    expectedResult.setTotalElements(1L);
    expectedResult.setTotalPages(1);
    expectedResult.setLast(true);

    // Configure AccountRepo.findByRoles_roleName(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Page<Account> accounts = new PageImpl<>(List.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null)))));
    when(mockAccountRepo.findByRoles_roleName(eq("name"), any(Pageable.class)))
        .thenReturn(accounts);

    // Run the test
    final RoleListResponse result = accountServiceImplUnderTest.getAccountByRole("name", 0, 5);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAccountByRole_AccountRepoReturnsNoItems() {
    // Setup
    final RoleListResponse expectedResult = new RoleListResponse();
    final AccountRequest accountRequest = new AccountRequest();
    accountRequest.setUsername("username");
    accountRequest.setEmail("email");
    accountRequest.setPassword("password");
    final RoleDto roleDto = new RoleDto();
    roleDto.setRoleId(0);
    roleDto.setRoleName("roleName");
    accountRequest.setRoles(List.of(roleDto));
    accountRequest.setFullName("fullName");
    accountRequest.setAge(0);
    accountRequest.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    expectedResult.setContent(List.of(accountRequest));
    expectedResult.setPageNo(0);
    expectedResult.setPageSize(5);
    expectedResult.setTotalElements(1L);
    expectedResult.setTotalPages(1);
    expectedResult.setLast(true);

    when(mockAccountRepo.findByRoles_roleName(eq("roleName"), any(Pageable.class)))
        .thenReturn(new PageImpl<>(Collections.emptyList()));

    // Run the test
    final RoleListResponse result = accountServiceImplUnderTest.getAccountByRole("roleName", 0, 5);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdatePassword() {
    // Setup
    final UpdatePasswordRequest passwordRequest = new UpdatePasswordRequest("password",
        "confirmPassword");
    when(mockPasswordEncoder.encode("password")).thenReturn("password");

    // Configure AccountRepo.save(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Account account = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.save(any(Account.class))).thenReturn(account);

    // Run the test
    accountServiceImplUnderTest.updatePassword(passwordRequest, 0);

    // Verify the results
    verify(mockAccountRepo).save(any(Account.class));
  }

  @Test
  void testAdminUpdate() {
    // Setup
    final AccountRequest accountRequest = new AccountRequest("username", "email", "password",
        List.of(new RoleDto(0, "roleName")), "fullName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final AccountRequest expectedResult = new AccountRequest("username", "email", "password",
        List.of(new RoleDto(0, "roleName")), "fullName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

    // Configure AccountRepo.save(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Account account = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.save(any(Account.class))).thenReturn(account);

    // Run the test
    final AccountRequest result = accountServiceImplUnderTest.adminUpdate(accountRequest, 0);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdateAccount() {
    // Setup
    final UpdateAccountRequest accountDto = new UpdateAccountRequest();
    accountDto.setFullName("fullName");
    accountDto.setAddress("address");
    accountDto.setAge(0);
    accountDto.setPhone("0912345678");

    final User user = new User("username", "password", false, false, false, false, List.of());
    final UpdateAccountRequest expectedResult = new UpdateAccountRequest();
    expectedResult.setFullName("fullName");
    expectedResult.setAddress("address");
    expectedResult.setAge(0);
    expectedResult.setPhone("0912345678");

    // Configure AccountRepo.findByUsername(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("0912345678");
    account1.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Account account = new Account(0, "email", "username", "password", "fullName", "address",
        0, "0912345678", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.findByUsername("username")).thenReturn(account);

    when(mockPhoneValidator.test("0912345678")).thenReturn(false);

    // Configure AccountRepo.save(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(0);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(0);
    account3.setPhone("phone");
    account3.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role1 = new Role();
    account3.setRoles(List.of(role1));
    userStatist1.setAccount(account3);
    final Account account2 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "0912345678", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Role(0, "roleName")), userStatist1, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)), null)));
    when(mockAccountRepo.save(any(Account.class))).thenReturn(account2);

    // Run the test
    final UpdateAccountRequest result = accountServiceImplUnderTest.updateAccount(accountDto, user);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(mockAccountRepo).save(any(Account.class));
  }
}
