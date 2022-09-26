package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.response.UserStatistResponse;
import com.example.project_vmo.models.response.AccountResponse;
import com.example.project_vmo.models.response.ListStatistResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.UserStaticRepo;
import com.example.project_vmo.services.UserStatistService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
  public UserStatistResponse createStatic(String usernameOrEmail) {
    Optional<Account> account = accountRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    if (account.isPresent()) {
      UserStatist userStatist = userStaticRepo.findByAccount(account.get());
      if (userStatist == null) {
        UserStatist userStatistNew = new UserStatist();
        userStatistNew.setAccount(account.get());
        userStatistNew.setCount(1);
        UserStatistResponse response = MapperUtil.map(userStaticRepo.save(userStatistNew), UserStatistResponse.class);
        response.setUsername(userStatistNew.getAccount().getUsername());
        return response;
      } else {
        UserStatist userStatistOld = userStaticRepo.findByAccount(account.get());
        userStatistOld.setCount(userStatist.getCount() + 1);
        userStatistOld.setAccount(account.get());
        userStaticRepo.save(userStatistOld);
        UserStatistResponse response = MapperUtil.map(userStatistOld, UserStatistResponse.class);
        response.setUsername(userStatistOld.getAccount().getUsername());
        return response;
      }
    } else {
      throw new RuntimeException("Account doesn't exist !");
    }
  }

  @Override
  public ListStatistResponse getAllStatic(int pageNo, int pageSize) {
    Page<UserStatist> statists = userStaticRepo.findAll(PageRequest.of(pageNo, pageSize));
    List<UserStatistResponse> statistDto = statists.getContent().stream()
        .map(statist -> new UserStatistResponse(
            statist.getStaticId(),
            statist.getCount(),
            statist.getAccount().getUsername(),
            statist.getLastLogin())).collect(
            Collectors.toList());
    ListStatistResponse response = new ListStatistResponse();
    response.setContent(statistDto);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalCount(userStaticRepo.getTotalAll());
    response.setTotalPages(statists.getTotalPages());
    response.setLast(statists.isLast());
    response.setCode(HttpStatus.ACCEPTED.value());
    return response;
  }

}
