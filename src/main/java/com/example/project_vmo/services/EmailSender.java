package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.PasswordResetToken;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;

public interface EmailSender {
  void sendEmail(String to, String email);
  void verifyExpiration(PasswordResetToken request);
  MessageResponse matchEmail(PasswordResetToken token, String requestEmail);
  PasswordResetToken getValidToken(PasswordResetRequest request);

  void inIsActiveToken(PasswordResetRequest request);

}
