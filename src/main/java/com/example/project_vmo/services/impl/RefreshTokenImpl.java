package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.exception.TokenRefreshException;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.RefreshToken;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.RefreshTokenRepo;
import com.example.project_vmo.services.RefreshTokenService;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenImpl implements RefreshTokenService {

  @Autowired
  private RefreshTokenRepo refreshTokenRepo;

  @Autowired
  private AccountRepo accountRepo;

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepo.findByToken(token);
  }

  @Override
  public RefreshToken createRefreshToken(String username) {
    RefreshToken refreshToken = new RefreshToken();
    Optional<Account> account = accountRepo.findByUsernameOrEmail(username,username);
    account.ifPresent(refreshToken::setAccount);
    int refreshTokenDurationMs = 120000;
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    return refreshTokenRepo.save(refreshToken);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepo.delete(token);
      throw new TokenRefreshException(token.getToken(),
          "Refresh token was expired. Please make a new login request");
    }
    return token;
  }

    @Override
    public int deleteByUserId ( int userId){
      return refreshTokenRepo.deleteByAccount(accountRepo.findByAccountId(userId));
    }
}
