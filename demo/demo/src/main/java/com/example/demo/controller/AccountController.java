package com.example.demo.controller;

import com.example.demo.dto.AccountDto;
import com.example.demo.exceptions.UserNotExistExceptions;
import com.example.demo.exceptions.ValidationExceptions;
import com.example.demo.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        try {
            return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
        }
        catch (ValidationExceptions e) {
            log.error("Validation Error! = {} {}", e.toString(), accountDto);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("addAccount {}", accountDto, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts()
    {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
    {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> getAccountByIdToDeposit(@PathVariable Long id, @RequestBody Map<String, Double> mapToDeposit)
    {
        AccountDto accountDto = accountService.deposit(id, mapToDeposit.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> getAccountByIdToWithdraw(@PathVariable Long id, @RequestBody Map<String, Double> mapToWithdraw)
    {
        AccountDto accountDto = accountService.withdraw(id, mapToWithdraw.get("withdraw"));
        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> getAccountByIdToDelete(@PathVariable Long id)
    {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Konto zostało usuniete pomyślnie!");

    }
}
