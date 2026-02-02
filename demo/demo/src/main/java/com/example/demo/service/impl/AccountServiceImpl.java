package com.example.demo.service.impl;

import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.exceptions.InvalidArgumentExceptions;
import com.example.demo.exceptions.RequiredFiledMissingExceptions;
import com.example.demo.exceptions.UserNotExistExceptions;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository)
    {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        walidate(accountDto);
        User user = userRepository
                .findById(accountDto.getUserId())
                .orElseThrow(()->new UserNotExistExceptions("User not found!", accountDto.getAccountHolderName()));

        Account account = AccountMapper.mapToAccount(accountDto, user);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    private void walidate(AccountDto accountDto) {
        if (accountDto.getAccountNumber() == null)
        {
            throw new RequiredFiledMissingExceptions("Required Account Number!");
        }
        try{
            Long.valueOf(accountDto.getAccountNumber());
        } catch (NumberFormatException e) {
            throw new InvalidArgumentExceptions("This String Not Number!");
        }

    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Konto nie istnieje!"));

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Konto nie istnieje"));

        account.setAmount(account.getAmount() + amount);
        Account saveAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Konto nie istnieje"));

        account.setAmount(account.getAmount() - amount);
        Account saveAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());


    }

    @Override
    public void deleteAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Konto nie istnieje!"));

         accountRepository.deleteById(id);
    }
}


