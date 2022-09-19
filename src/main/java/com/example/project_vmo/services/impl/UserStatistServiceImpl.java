package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.request.UserStatistDto;
import com.example.project_vmo.models.response.UserStatistResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.UserStaticRepo;
import com.example.project_vmo.services.UserStatistService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  public UserStatistResponse getAllStatic(int pageNo, int pageSize) {
    Page<UserStatist> statists = userStaticRepo.findAll(PageRequest.of(pageNo, pageSize));
    List<UserStatistDto> statistDto = statists.getContent().stream()
        .map(statist -> MapperUtil.map(statist, UserStatistDto.class)).collect(
            Collectors.toList());
    UserStatistResponse response = new UserStatistResponse();
    response.setContent(statistDto);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalCount(userStaticRepo.getTotalAll());
    response.setTotalPages(statists.getTotalPages());
    response.setLast(statists.isLast());
    return response;
  }
}
