package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.commons.exception.ResourceAlreadyExistsException;
import com.example.project_vmo.commons.exception.ResourceNotFoundException;
import com.example.project_vmo.commons.filters.PhoneValidator;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountRequest;
import com.example.project_vmo.models.request.UpdateAccountRequest;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.AccountResponse;
import com.example.project_vmo.models.response.RoleListResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.services.AccountService;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
  public List<AccountRequest> getAll() {
    return accountRepo.findAll().stream().map(account -> MapperUtil.map(account, AccountRequest.class))
        .collect(
            Collectors.toList());
  }

  @Override
  public AccountResponse createAccount(AccountRequest accountRequest) {
    Account account = MapperUtil.map(accountRequest, Account.class);
    if (emailExists(account.getEmail())){
      throw new ResourceAlreadyExistsException("Email already taken");
    }
    if (usernameExists(account.getUsername())){
      throw new ResourceAlreadyExistsException("Username already taken");
    }
    List<Role> roles = MapperUtil.mapList(accountRequest.getRoles(),Role.class);
    account.setRoles(roles);
    account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
    accountRepo.save(account);
    return new AccountResponse(account.getAccountId(), account.getUsername());
  }


  @Override
  public void deleteAccount(int id) {
    Account account = MapperUtil.map(accountRepo.findByAccountId(id), Account.class);
    account.setIs_deleted(true);
    MapperUtil.map(account, AccountRequest.class);
  }

  @Override
  public RoleListResponse getAccountByRole(String name,int pageNo,int pageSize) {
    Pageable pageable = PageRequest.of(pageNo,pageSize);
    Page<Account> accounts = accountRepo.findByRoles_roleName(name,pageable);
    List<AccountResponse> content = accounts.getContent().stream().map(account -> MapperUtil.map(account,
        AccountResponse.class)).collect(
        Collectors.toList());
    RoleListResponse response = new RoleListResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(accounts.getTotalElements());
    response.setTotalPages(accounts.getTotalPages());
    response.setLast(accounts.isLast());
    response.setCode(HttpStatus.ACCEPTED.value());
    return  response;
  }

  @Override
  public void updatePassword(UpdatePasswordRequest passwordRequest, int id) {
    Account account = MapperUtil.map(passwordRequest, Account.class);
    account.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
    accountRepo.save(account);
  }

  @Override
  public AccountRequest adminUpdate(AccountRequest accountRequest, int id) {
    Account account = MapperUtil.map(accountRepo.findByAccountId(id), Account.class);    ;
    return MapperUtil.map(accountRepo.save(account), AccountRequest.class);
  }

  @Override
  public UpdateAccountRequest updateAccount(UpdateAccountRequest accountDto, User user) {
    Account account = accountRepo.findByUsername(user.getUsername());
    if (account == null){
      throw new ResourceNotFoundException("Not found" + user.getUsername());
    }
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
    return MapperUtil.map(account, UpdateAccountRequest.class);
  }

  private boolean emailExists(String email) {
    return accountRepo.findByEmail(email).isPresent();
  }

  private boolean usernameExists(String username) {
    return accountRepo.findByUsername(username) != null;
  }

}
