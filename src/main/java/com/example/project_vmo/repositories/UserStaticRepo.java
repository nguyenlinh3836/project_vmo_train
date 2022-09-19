package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatist;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserStaticRepo extends JpaRepository<UserStatist, Integer> {

  @Query("SELECT SUM(m.count) FROM UserStatist m")
  int getTotalAll();

  UserStatist findByAccount(Account account);
}
