package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStaticRepo extends JpaRepository<UserStatist,Integer> {
UserStatist findByAccount(Account account);
}
