package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.repositories.AccountRepo;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  private AccountRepo mockAccountRepo;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsServiceImplUnderTest;

  @Test
  void testLoadUserByUsername() {
    // Setup
    // Configure AccountRepo.findByUsernameOrEmail(...).
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
    account1.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Optional<Account> account = Optional.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Image(0, "name", "fileType", "imageUrl",
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)), null))));
    when(mockAccountRepo.findByUsernameOrEmail("usernameOrEmail", "usernameOrEmail"))
        .thenReturn(account);

    // Run the test
    final UserDetails result = userDetailsServiceImplUnderTest.loadUserByUsername(
        "usernameOrEmail");

    // Verify the results
  }

  @Test
  void testLoadUserByUsername_AccountRepoReturnsAbsent() {
    // Setup
    when(mockAccountRepo.findByUsernameOrEmail("usernameOrEmail", "usernameOrEmail"))
        .thenReturn(Optional.empty());

    // Run the test
    assertThatThrownBy(
        () -> userDetailsServiceImplUnderTest.loadUserByUsername("usernameOrEmail"))
        .isInstanceOf(UsernameNotFoundException.class);
  }

  @Test
  void testLoadUserByUsername_ThrowsUsernameNotFoundException() {
    // Setup
    // Configure AccountRepo.findByUsernameOrEmail(...).
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
    account1.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role = new Role();
    account1.setRoles(List.of(role));
    userStatist.setAccount(account1);
    final Optional<Account> account = Optional.of(
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Image(0, "name", "fileType", "imageUrl",
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)), null))));
    when(mockAccountRepo.findByUsernameOrEmail("usernameOrEmail", "usernameOrEmail"))
        .thenReturn(account);

    // Run the test
    assertThatThrownBy(
        () -> userDetailsServiceImplUnderTest.loadUserByUsername("usernameOrEmail"))
        .isInstanceOf(UsernameNotFoundException.class);
  }
}
