package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.exception.ResourceNotFoundException;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.PasswordResetToken;
import com.example.project_vmo.models.request.PasswordResetLinkRequest;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.PasswordResetTokenRepo;
import com.example.project_vmo.services.EmailSender;
import com.example.project_vmo.services.PasswordTokenService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService {

  @Autowired
  private PasswordResetTokenRepo passwordResetTokenRepo;
  @Autowired
  private AccountRepo accountRepo;
  @Autowired
  private EmailSender emailSender;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final String url = "http://localhost:8081/api/auth/forgot-password/confirm?token=";

  @Override
  public String confirmTokenResetPassword(String token) {
    Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepo.findByToken(token);
    if (passwordResetToken.isPresent()) {
      if (passwordResetToken.get().getConfirmedAt() != null){
        throw new IllegalStateException("Email is already confirmed");
      }
      LocalDateTime expiresAt = passwordResetToken.get().getExpiryDate();
      if (expiresAt.isBefore(LocalDateTime.now())) {
        throw new IllegalStateException("Token is already expired!");
      }
      passwordResetTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
      //Returning confirmation message if the token matches
      return "Your email is confirmed. Please input new password to reset password!!!";
    }
    return "Your Email not confirmed. Please try again !!!";
  }

  @Override
  public String sendTokenToChangePassword(PasswordResetLinkRequest request) {
    boolean isExist = accountRepo.findByEmail(request.getEmail()).isPresent();
    if(isExist){
      Account account = accountRepo.findByEmail(request.getEmail()).get();
      String token = UUID.randomUUID().toString();
      saveConfirmationToken(account, token);
      String link = url + token;
      emailSender.sendEmail(account.getEmail(), buildEmail(account.getUsername(), link));
      return "Success: Token send successfully!" + token;
    } else {
      return "Fail: Email is not found";
    }
  }

  @Override
  public MessageResponse changePassword(PasswordResetRequest request) {
    PasswordResetToken token = emailSender.getValidToken(request);
    if (request.getToken().equals(token.getToken())) {
      updatePassword(request.getEmail(), request.getPassword());
      passwordResetTokenRepo.delete(passwordResetTokenRepo.findByToken(request.getToken()).get());
      return new MessageResponse("Change password successfully!!");
    }
    return new MessageResponse("Change password fail!!");
  }

  @Override
  public Boolean updatePassword(String email, String password) {
    Account account = accountRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Find email","email", email ));
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);
    accountRepo.save(account);
    return true;
  }

  @Override
  public void saveConfirmationToken(Account account, String token) {
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setAccount(account);
    passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
    passwordResetToken.setActive(true);
    passwordResetTokenRepo.save(passwordResetToken);
  }

  @Override
  public String buildEmail(String name, String link) {
    return
        "<div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n"
            +
            "    &nbsp;\n" +
            "</div>\n" +
            "<table style=\"background: #F7F8FA; border: 0; border-radius: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n"
            +
            "<tbody>\n" +
            "<tr>\n" +
            "    <td class=\"tw-body\" style=\"padding: 15px 15px 0;\" align=\"center\">\n" +
            "        <table style=\"background: #F7F8FA; border: 0; border-radius: 0;\" cellspacing=\"0\" cellpadding=\"0\">\n"
            +
            "            <tbody>\n" +
            "            <tr>\n" +
            "                <td class=\"\" style=\"width: 600px;\" align=\"center\">\n" +
            "                    <p style=\"padding: 5px 5px 5px; font-size: 13px; margin: 0 0 0px; color: #316fea;\" align=\"right\">\n"
            +
            "                    </p>\n" +
            "                    <table style=\"background: #ffffff; border: 0px; border-radius: 4px; width: 99.6672%; overflow: hidden;\"\n"
            +
            "                           cellspacing=\"0\" cellpadding=\"0\">\n" +
            "                        <tbody>\n" +
            "                        <tr>\n" +
            "                            <td class=\"\" style=\"padding: 0px; width: 100%;\" align=\"center\">\n"
            +
            "                                <table style=\"background: #336f85; border: 0px; border-radius: 0px; width: 599px; height: 53px; margin-left: auto; margin-right: auto;\"\n"
            +
            "                                       cellspacing=\"0\" cellpadding=\"0\">\n" +
            "                                    <tbody>\n" +
            "                                    <tr>\n" +
            "\n" +
            "                                        <td class=\"tw-card-header\"\n" +
            "                                            style=\"padding: 5px 5px px; width: 366px; color: #ffff; text-decoration: none; font-family: sans-serif;\"\n"
            +
            "                                            align=\"center\"><span\n" +
            "                                                style=\"font-weight: 600;\">Email Confirmation Required</span></td>\n"
            +
            "\n" +
            "                                    </tr>\n" +
            "                                    </tbody>\n" +
            "                                </table>\n" +
            "                                <p><br/><br/></p>\n" +
            "                                <table dir=\"ltr\" style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n"
            +
            "                                    <tbody>\n" +
            "                                    <tr>\n" +
            "                                        <td class=\"tw-card-body\"\n" +
            "                                            style=\"padding: 20px 35px; text-align: left; color: #6f6f6f; font-family: sans-serif; border-top: 0;\">\n"
            +
            "                                            <h1 class=\"tw-h1\"\n" +
            "                                                style=\"font-size: 24px; font-weight: bold; mso-line-height-rule: exactly; line-height: 32px; margin: 0 0 20px; color: #474747;\">\n"
            +
            "                                                Hello " + name + ",</h1>\n" +
            "                                            <p class=\"\"\n" +
            "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n"
            +
            "                                            <span style=\"font-weight: 400;\">Thank you for joining <strong>Customer App</strong>,welcom to join us!</span><br/><br/><span\n"
            +
            "                                                style=\"font-weight: 400;\">To complete the registration process, please confirm your email address to activate your account</span>\n"
            +
            "                                            <table style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n"
            +
            "                                                <tbody>\n" +
            "                                                <tr>\n" +
            "                                                    <td>\n" +
            "                                                        <table class=\"button mobile-w-full\"\n"
            +
            "                                                               style=\"border: 0px; border-radius: 7px; margin: 0px auto; width: 525px; background-color: #008bcb; height: 50px;\"\n"
            +
            "                                                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
            +
            "                                                            <tbody>\n" +
            "                                                            <tr>\n" +
            "                                                                <td class=\"button__td \"\n"
            +
            "                                                                    style=\"border-radius: 7px; text-align: center; width: 523px;\"><!-- [if mso]>\n"
            +
            "                                                                    <a href=\"\" class=\"button__a\" target=\"_blank\"\n"
            +
            "                                                                       style=\"border-radius: 4px; color: #FFFFFF; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 1px solid #316FEA;\"> </a>\n"
            +
            "                                                                    <![endif]--> <!-- [if !mso]><!--> <a\n"
            +
            "                                                                            class=\"button__a\"\n"
            +
            "                                                                            style=\"border-radius: 4px; color: #ffffff; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 0;\"\n"
            +
            "                                                                            href=\""
            + link + "\"\n" +
            "                                                                            target=\"_blank\"\n"
            +
            "                                                                            rel=\"noopener\">Confirm\n"
            +
            "                                                                        email</a> <!--![endif]--></td>\n"
            +
            "                                                            </tr>\n" +
            "                                                            </tbody>\n" +
            "                                                        </table>\n" +
            "                                                    </td>\n" +
            "                                                </tr>\n" +
            "                                                </tbody>\n" +
            "                                            </table>\n" +
            "                                            <div class=\"\"\n" +
            "                                                 style=\"border-top: 0; font-size: 1px; mso-line-height-rule: exactly; line-height: 1px; max-height: 0; margin: 20px 0; overflow: hidden;\">\n"
            +
            "                                                \u200B\n" +
            "                                            </div>\n" +
            "                                            <p class=\"\"\n" +
            "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n"
            +
            "                                                Contact our support team if you have any questions or concerns.&nbsp;<a\n"
            +
            "                                                    style=\"color: #316fea; text-decoration: none;\"\n"
            +
            "                                                    href=\"javascript:void(0);\" target=\"_blank\" rel=\"noopener\">Ask us any\n"
            +
            "                                                question</a></p>\n" +
            "                                            <p class=\"tw-signoff\"\n" +
            "                                               style=\"margin: 45px 0 5px; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n"
            +
            "                                                Our best, <br/> The Customer Engagement App team</p>\n"
            +
            "                                        </td>\n" +
            "                                    </tr>\n" +
            "                                    </tbody>\n" +
            "                                </table>\n" +
            "                            </td>\n" +
            "                        </tr>\n" +
            "                        </tbody>\n" +
            "                    </table>";
  }
}
