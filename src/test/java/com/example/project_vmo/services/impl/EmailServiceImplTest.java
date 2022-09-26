package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.entities.PasswordResetToken;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.repositories.PasswordResetTokenRepo;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock
  private JavaMailSender mockMailSender;
  @Mock
  private PasswordResetTokenRepo mockPasswordResetTokenRepo;

  @InjectMocks
  private EmailServiceImpl emailServiceImplUnderTest;

  @Test
  void testSendEmail() {
    // Setup
    // Configure JavaMailSender.createMimeMessage(...).
    final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
    when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);

    // Run the test
    emailServiceImplUnderTest.sendEmail("to", "email");

    // Verify the results
    verify(mockMailSender).send(any(MimeMessage.class));
  }

  @Test
  void testSendEmail_JavaMailSenderSendThrowsMailException() {
    // Setup
    // Configure JavaMailSender.createMimeMessage(...).
    final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
    when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);

    doThrow(MailException.class).when(mockMailSender).send(any(MimeMessage.class));

    // Run the test
    assertThatThrownBy(() -> emailServiceImplUnderTest.sendEmail("to", "email"))
        .isInstanceOf(MailException.class);
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
    final PasswordResetToken request = new PasswordResetToken(0, "token",
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))), LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false);

    // Run the test
    emailServiceImplUnderTest.verifyExpiration(request);

    // Verify the results
  }

  @Test
  void testMatchEmail() {
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
    final PasswordResetToken token = new PasswordResetToken(0, "token",
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))), LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false);
    final MessageResponse expectedResult = new MessageResponse(0, "message");

    // Run the test
    final MessageResponse result = emailServiceImplUnderTest.matchEmail(token, "requestEmail");

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetValidToken() {
    // Setup
    final PasswordResetRequest request = new PasswordResetRequest();
    request.setEmail("email");
    request.setPassword("password");
    request.setConfirmPassword("confirmPassword");
    request.setToken("token");

    // Configure PasswordResetTokenRepo.findByToken(...).
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
    final Optional<PasswordResetToken> passwordResetToken = Optional.of(
        new PasswordResetToken(0, "token",
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Role(0, "roleName")), userStatist, List.of(new Good(0, "goodsName", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))), LocalDateTime.of(2020, 1, 1, 0, 0, 0),
            LocalDateTime.of(2020, 1, 1, 0, 0, 0), false));
    when(mockPasswordResetTokenRepo.findByToken("token")).thenReturn(passwordResetToken);

    // Run the test
    final PasswordResetToken result = emailServiceImplUnderTest.getValidToken(request);

    // Verify the results
  }

  @Test
  void testGetValidToken_PasswordResetTokenRepoReturnsAbsent() {
    // Setup
    final PasswordResetRequest request = new PasswordResetRequest();
    request.setEmail("email");
    request.setPassword("password");
    request.setConfirmPassword("confirmPassword");
    request.setToken("token");

    when(mockPasswordResetTokenRepo.findByToken("token")).thenReturn(Optional.empty());

    // Run the test
    assertThatThrownBy(() -> emailServiceImplUnderTest.getValidToken(request))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void testInIsActiveToken() {
    // Setup
    final PasswordResetRequest request = new PasswordResetRequest();
    request.setEmail("email");
    request.setPassword("password");
    request.setConfirmPassword("confirmPassword");
    request.setToken("token");

    // Configure PasswordResetTokenRepo.save(...).
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
    final PasswordResetToken passwordResetToken = new PasswordResetToken(0, "token",
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Role(0, "roleName")), userStatist, List.of(
            new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                List.of(new Image(0, "name", null)), null))), LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false);
    when(mockPasswordResetTokenRepo.save(any(PasswordResetToken.class)))
        .thenReturn(passwordResetToken);

    // Run the test
    emailServiceImplUnderTest.inIsActiveToken(request);

    // Verify the results
    verify(mockPasswordResetTokenRepo).save(any(PasswordResetToken.class));
  }
}
