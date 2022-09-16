package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByAccount(Account account);
}
