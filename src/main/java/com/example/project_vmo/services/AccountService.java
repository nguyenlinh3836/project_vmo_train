package com.example.project_vmo.services;

import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.UpdateAccountDto;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.RoleListResponse;
import com.example.project_vmo.security.jwt.CustomUserDetail;
import java.util.List;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.security.core.userdetails.User;

public interface AccountService {

  List<AccountDto> getAll();

  AccountDto createAccount(AccountDto accountDto);


  void deleteAccount(int id);

  RoleListResponse getAccountByRole(String name, int pageNo, int pageSize);

  UpdateAccountDto updateAccount(UpdateAccountDto accountDto, User user);

  void updatePassword(UpdatePasswordRequest passwordRequest, int id);

  AccountDto adminUpdateDto(AccountDto accountDto,int id);
}
