package com.example.project_vmo.services;

import com.example.project_vmo.models.request.AccountRequest;
import com.example.project_vmo.models.request.UpdateAccountRequest;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.AccountResponse;
import com.example.project_vmo.models.response.RoleListResponse;
import java.util.List;
import org.springframework.security.core.userdetails.User;

public interface AccountService {

  List<AccountRequest> getAll();

  AccountResponse createAccount(AccountRequest accountRequest);


  void deleteAccount(int id);

  RoleListResponse getAccountByRole(String name, int pageNo, int pageSize);

  UpdateAccountRequest updateAccount(UpdateAccountRequest accountDto, User user);

  void updatePassword(UpdatePasswordRequest passwordRequest, int id);

  AccountRequest adminUpdate(AccountRequest accountRequest,int id);
}
