package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.response.AccountCreateResponse;
import com.example.project_vmo.services.AccountService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @Autowired
  private AccountService accountService;

  @PostMapping
  public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto) {
    AccountCreateResponse response = new AccountCreateResponse();
    response.setAccountDto(accountService.createAccount(accountDto));
    response.setMessage("New account has been create !");
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(response);
  }

  @GetMapping
  public ResponseEntity<?> getAccountByRole(@RequestParam String name,
      @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(accountService.getAccountByRole(name, pageNo, pageSize));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAccount(@RequestBody AccountDto accountDto,
      @PathVariable("id") int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(accountService.adminUpdateDto(accountDto, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAccount(@PathVariable("id") int id) {
    accountService.deleteAccount(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Buyer has been deleted");
  }
}
