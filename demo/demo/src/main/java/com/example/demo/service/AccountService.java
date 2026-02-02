package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccountById(Long id);
}
