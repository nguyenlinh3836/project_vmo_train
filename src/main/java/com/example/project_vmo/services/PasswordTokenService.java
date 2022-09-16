package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.request.PasswordResetLinkRequest;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface PasswordTokenService {

  String confirmTokenResetPassword(String token);

  String sendTokenToChangePassword(PasswordResetLinkRequest request);

  MessageResponse changePassword(PasswordResetRequest request);

  Boolean updatePassword(String email, String password);

  void saveConfirmationToken(Account account, String token);

  String buildEmail(String name, String link);
}
