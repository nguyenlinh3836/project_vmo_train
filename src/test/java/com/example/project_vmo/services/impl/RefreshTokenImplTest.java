package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.RefreshToken;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.RefreshTokenRepo;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenImplTest {

  @Mock
  private RefreshTokenRepo mockRefreshTokenRepo;
  @Mock
  private AccountRepo mockAccountRepo;

  @InjectMocks
  private RefreshTokenImpl refreshTokenImplUnderTest;

  @Test
  void testFindByToken() {
    // Setup
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
    final Optional<RefreshToken> expectedResult = Optional.of(new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC)));

    // Configure RefreshTokenRepo.findByToken(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
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
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final Optional<RefreshToken> refreshToken = Optional.of(new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist1), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC)));
    when(mockRefreshTokenRepo.findByToken("token")).thenReturn(refreshToken);

    // Run the test
    final Optional<RefreshToken> result = refreshTokenImplUnderTest.findByToken("token");

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testFindByToken_RefreshTokenRepoReturnsAbsent() {
    // Setup
    when(mockRefreshTokenRepo.findByToken("token")).thenReturn(Optional.empty());

    // Run the test
    final Optional<RefreshToken> result = refreshTokenImplUnderTest.findByToken("token");

    // Verify the results
    assertThat(result).isEmpty();
  }

  @Test
  void testCreateRefreshToken() {
    // Setup
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
    final RefreshToken expectedResult = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));

    // Configure AccountRepo.findByUsernameOrEmail(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account2 = new Account();
    account2.setAccountId(0);
    account2.setEmail("email");
    account2.setUsername("username");
    account2.setPassword("password");
    account2.setFullName("fullName");
    account2.setAddress("address");
    account2.setAge(0);
    account2.setPhone("phone");
    account2.setCreate_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account2.setIs_deleted(false);
    final Role role1 = new Role();
    account2.setRoles(List.of(role1));
    userStatist1.setAccount(account2);
    final Optional<Account> account1 = Optional.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist1));
    when(mockAccountRepo.findByUsernameOrEmail("username", "username")).thenReturn(account1);

    // Configure RefreshTokenRepo.save(...).
    final UserStatist userStatist2 = new UserStatist();
    userStatist2.setStaticId(0);
    userStatist2.setCount(0);
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
    final Role role2 = new Role();
    account3.setRoles(List.of(role2));
    userStatist2.setAccount(account3);
    final RefreshToken refreshToken = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist2), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
    when(mockRefreshTokenRepo.save(new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), new UserStatist()),
        "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC))))
        .thenReturn(refreshToken);

    // Run the test
    final RefreshToken result = refreshTokenImplUnderTest.createRefreshToken("username");

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testCreateRefreshToken_AccountRepoReturnsAbsent() {
    // Setup
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
    final RefreshToken expectedResult = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
    when(mockAccountRepo.findByUsernameOrEmail("username", "username"))
        .thenReturn(Optional.empty());

    // Configure RefreshTokenRepo.save(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
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
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final RefreshToken refreshToken = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist1), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
    when(mockRefreshTokenRepo.save(new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), new UserStatist()),
        "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC))))
        .thenReturn(refreshToken);

    // Run the test
    final RefreshToken result = refreshTokenImplUnderTest.createRefreshToken("username");

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testVerifyExpiration() {
    // Setup
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
    final RefreshToken token = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
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
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final RefreshToken expectedResult = new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist1), "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));

    // Run the test
    final RefreshToken result = refreshTokenImplUnderTest.verifyExpiration(token);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(mockRefreshTokenRepo).delete(new RefreshToken(0L,
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), new UserStatist()),
        "c11c9dc5-4128-4cf5-bdc5-0815e0a6dcfd",
        LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC)));
  }

  @Test
  void testDeleteByUserId() {
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
        List.of(new Role(0, "roleName")), userStatist);
    when(mockAccountRepo.findByAccountId(0)).thenReturn(account);

    when(mockRefreshTokenRepo.deleteByAccount(any(Account.class))).thenReturn(0);

    // Run the test
    final int result = refreshTokenImplUnderTest.deleteByUserId(0);

    // Verify the results
    assertThat(result).isEqualTo(0);
  }
}
