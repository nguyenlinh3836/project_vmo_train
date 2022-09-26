package com.example.project_vmo.controllers;


import com.example.project_vmo.models.request.UpdateAccountRequest;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.services.AccountService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

  @Autowired
  private AccountService accountService;

  @PutMapping()
  public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateAccountRequest accountRequest,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(accountService.updateAccount(accountRequest, user ));
  }

  @PutMapping("/changePassword/{id}")
  public ResponseEntity<?> updatePassword(@PathVariable("id") @Valid int id,@RequestBody @Valid
      UpdatePasswordRequest updatePasswordRequest){
    accountService.updatePassword(updatePasswordRequest,id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been change !");
  }




}
