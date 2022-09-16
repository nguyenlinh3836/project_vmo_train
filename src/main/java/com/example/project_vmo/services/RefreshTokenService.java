package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.RefreshToken;
import java.util.Optional;

public interface RefreshTokenService {
  Optional<RefreshToken> findByToken(String token);
  RefreshToken createRefreshToken(String username);
  RefreshToken verifyExpiration(RefreshToken token);
  int deleteByUserId(int id);
}
