package com.example.project_vmo.repositories;


import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.request.AccountDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepo extends JpaRepository<Account, Integer> {

  Optional<Account> findByUsernameOrEmail(String name, String email);

  Account findByUsername(String name);

  Optional<Account> findByEmail(String email);

  Account findByAccountId(int id);

  Page<Account> findByRoles_roleName(String roleName, Pageable pageable);

}
