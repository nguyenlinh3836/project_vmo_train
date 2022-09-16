package com.example.project_vmo.services.impl;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.UserStaticRepo;
import com.example.project_vmo.services.UserStatistService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserStatistServiceImpl implements UserStatistService {

  @Autowired
  private UserStaticRepo userStaticRepo;
  @Autowired
  private AccountRepo accountRepo;

  @Override
  public void createStatic(String usernameOrEmail) {
    Optional<Account> account = accountRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    if (account.isPresent()) {
      UserStatist userStatist = userStaticRepo.findByAccount(account.get());
      if (userStatist == null) {
        UserStatist userStatistNew = new UserStatist();
        userStatistNew.setAccount(account.get());
        userStatistNew.setCount(1);
        userStaticRepo.save(userStatistNew);
      } else {
        UserStatist userStatistOld = userStaticRepo.findByAccount(account.get());
        userStatistOld.setCount(userStatist.getCount() + 1);
        userStaticRepo.save(userStatistOld);
      }
    }
  }

  @Override
  public List<UserStatist> getAllStatic(int pageNo, int pageSize) {
    return null;
  }
}
