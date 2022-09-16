package com.example.project_vmo.services.impl;

import com.example.project_vmo.models.entities.PasswordResetToken;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.repositories.PasswordResetTokenRepo;
import com.example.project_vmo.services.EmailSender;
import java.time.LocalDateTime;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailSender {
  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private PasswordResetTokenRepo passwordResetTokenRepo;
  private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Override
  public void sendEmail(String to, String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(email, true);
      helper.setTo(to);
      helper.setSubject("Confirm your email");
      helper.setFrom("darksidervn@gmail.com");
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      logger.error("Failed to send email for: " + email + "\n" + e);
      throw new IllegalArgumentException("Failed to send email for: " + email);
    }

  }

  @Override
  public void verifyExpiration(PasswordResetToken request) {
    if (request.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Token is already expired!");
    }
    if(!request.getActive()){
      throw new IllegalStateException("Token was marked is inactive");
    }
  }

  @Override
  public MessageResponse matchEmail(PasswordResetToken token, String requestEmail) {
    if(token.getAccount().getEmail().compareToIgnoreCase(requestEmail) != 0){
      return new MessageResponse("Token is invalid for given account");
    }
    return new MessageResponse("Token is valid");
  }

  @Override
  public PasswordResetToken getValidToken(PasswordResetRequest request) {
    String tokenName = request.getToken();
    PasswordResetToken token = passwordResetTokenRepo.findByToken(tokenName).get();
    matchEmail(token, request.getEmail());
    verifyExpiration(token);
    return token;
  }

  @Override
  public void inIsActiveToken(PasswordResetRequest request) {
    String token = request.getToken();
    PasswordResetToken newPassToken = new PasswordResetToken();
    newPassToken.setToken(token);
    newPassToken.setActive(false);
    passwordResetTokenRepo.save(newPassToken);
  }
}
