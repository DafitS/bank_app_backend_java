package com.example.demo.controller;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.dto.account.AmountDto;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> addAccount(
            @RequestBody AccountCreateDto accountCreateDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(accountCreateDto));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE')")
    @GetMapping("/all")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts()
    {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE') or @accountSecurity.isOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id)
    {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or @accountSecurity.isOwner(#id)")
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDto> getAccountByIdToDeposit(@PathVariable Long id,  @Valid @RequestBody AmountDto amountDto)
    {
        AccountResponseDto accountResponseDto = accountService.deposit(id, amountDto.getAmount());
        return ResponseEntity.ok(accountResponseDto);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or @accountSecurity.isOwner(#id)")
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDto> getAccountByIdToWithdraw(@PathVariable Long id,  @Valid @RequestBody AmountDto amountDto)
    {
        AccountResponseDto accountResponseDto = accountService.withdraw(id, amountDto.getAmount());
        return ResponseEntity.ok(accountResponseDto);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or @accountSecurity.isOwner(#id)")
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> getAccountByIdToDelete(@PathVariable Long id)
    {
        accountService.closeAccountById(id);
        return ResponseEntity.ok("Konto zostało wyłączone pomyślnie!");

    }
}
