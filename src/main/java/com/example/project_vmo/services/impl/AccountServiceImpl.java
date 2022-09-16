package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.commons.filters.PhoneValidator;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.UpdateAccountDto;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.RoleListResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.services.AccountService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {


  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private PhoneValidator phoneValidator;

  @Override
  public List<AccountDto> getAll() {
    return accountRepo.findAll().stream().map(account -> MapperUtil.map(account, AccountDto.class))
        .collect(
            Collectors.toList());
  }

  @Override
  public AccountDto createAccount(AccountDto accountDto) {
    Account account = MapperUtil.map(accountDto, Account.class);
    if (emailExists(account.getEmail())){
      throw new IllegalStateException("Email already exists !");
    }
    if (usernameExists(account.getUsername())){
      throw new IllegalStateException("Username already exists !");
    }
    List<Role> roles = MapperUtil.mapList(accountDto.getRoles(),Role.class);
    account.setRoles(roles);
    account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    return MapperUtil.map(accountRepo.save(account), AccountDto.class);
  }


  @Override
  public void deleteAccount(int id) {
    Account account = MapperUtil.map(accountRepo.findByAccountId(id), Account.class);
    account.setIs_deleted(true);
    MapperUtil.map(account, AccountDto.class);
  }

  @Override
  public RoleListResponse getAccountByRole(String name,int pageNo,int pageSize) {
    Pageable pageable = PageRequest.of(pageNo,pageSize);
    Page<Account> accounts = accountRepo.findByRoles_roleName(name,pageable);
    List<AccountDto> content = accounts.getContent().stream().map(account -> MapperUtil.map(account,AccountDto.class)).collect(
        Collectors.toList());
    RoleListResponse response = new RoleListResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(accounts.getTotalElements());
    response.setTotalPages(accounts.getTotalPages());
    response.setLast(accounts.isLast());
    return  response;
  }

  @Override
  public void updatePassword(UpdatePasswordRequest passwordRequest, int id) {
    Account account = MapperUtil.map(passwordRequest, Account.class);
    account.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
    accountRepo.save(account);
  }

  @Override
  public AccountDto adminUpdateDto(AccountDto accountDto, int id) {
    Account account = MapperUtil.map(accountDto, Account.class);
    account.setAccountId(id);
    return MapperUtil.map(accountRepo.save(account), AccountDto.class);
  }

  @Override
  public UpdateAccountDto updateAccount(UpdateAccountDto accountDto, User user) {
    Account account = accountRepo.findByUsername(user.getUsername());
    account.setFullName(accountDto.getFullName());
    account.setAddress(accountDto.getAddress());
    account.setAge(accountDto.getAge());
    if (!StringUtils.isEmpty(accountDto.getPhone())) {
      boolean isValidPhone = phoneValidator.test(accountDto.getPhone());
      if (!isValidPhone) {
        throw new IllegalStateException("Phone number is not valid");
      } else {
        account.setPhone(accountDto.getPhone());
      }
    }
    accountRepo.save(account);
    return MapperUtil.map(account, UpdateAccountDto.class);
  }

  private boolean emailExists(String email) {
    return accountRepo.findByEmail(email).isPresent();
  }

  private boolean usernameExists(String username) {
    return accountRepo.findByUsername(username) != null;
  }

}
