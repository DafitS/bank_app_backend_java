package com.example.demo.service;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponseDto createAccount(AccountCreateDto accountCreateDto);

    AccountResponseDto getAccountById(Long id);

    AccountResponseDto deposit(Long id, BigDecimal amount);

    AccountResponseDto withdraw(Long id, BigDecimal amount);

    List<AccountResponseDto> getAllAccounts();

    void closeAccountById(Long id);
}
