package com.example.demo.service.impl;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.OperationHistory;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AccountNotExistException;
import com.example.demo.exceptions.InvalidArgumentException;
import com.example.demo.exceptions.RequiredFiledMissingException;
import com.example.demo.exceptions.UserNotExistException;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.option.OperationType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OperationHistoryRepository historyRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, OperationHistoryRepository historyRepository)
    {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public AccountResponseDto createAccount(AccountCreateDto accountCreateDto) {
        validate(accountCreateDto);
        User user = userRepository
                .findById(accountCreateDto.getUserId())
                .orElseThrow(()->new UserNotExistException("User not found!", accountCreateDto.getAccountHolderName()));

        Account account = AccountMapper.mapToAccount(accountCreateDto, user);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountResponseDto(savedAccount);
    }

    private void validate(AccountCreateDto accountDto) {
        if (accountDto.getAccountNumber() == null || accountDto.getAccountNumber().isBlank())
        {
            throw new RequiredFiledMissingException("Required Account Number!");
        }
        if (accountDto.getUserId() == null)
        {
            throw new RequiredFiledMissingException("UserID is Required!");
        }
        try{
            Long accountNumber = Long.valueOf(accountDto.getAccountNumber());
            if(accountNumber <= 0)
            {
                throw new InvalidArgumentException("Account number must be positive");
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("This String Not Number!");
        }

    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountNotExistException("Account not Found!", id));

        return AccountMapper.mapToAccountResponseDto(account);
    }

    @Override
    public AccountResponseDto deposit(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Deposit amount must be greater than zero");
        }
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountNotExistException("Account not Found!", id));

        account.setAmount(account.getAmount().add(amount));
        Account saveAccount = accountRepository.save(account);

        OperationHistory history = new OperationHistory();
        history.setAccount(saveAccount);
        history.setOperationType(OperationType.DEPOSIT);
        history.setAmount(amount);
        history.setBalanceAfter(saveAccount.getAmount());
        history.setCreatedAt(LocalDateTime.now());

        historyRepository.save(history);

        return AccountMapper.mapToAccountResponseDto(saveAccount);
    }

    @Override
    public AccountResponseDto withdraw(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Withdraw amount must be greater than zero");
        }
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountNotExistException("Account not Found!", id));

        if (account.getAmount().compareTo(amount) < 0) {
            throw new InvalidArgumentException("No funds to withdraw!");
        }

        account.setAmount(account.getAmount().subtract(amount));
        Account saveAccount = accountRepository.save(account);

        OperationHistory history = new OperationHistory();
        history.setAccount(saveAccount);
        history.setOperationType(OperationType.WITHDRAW);
        history.setAmount(amount);
        history.setBalanceAfter(saveAccount.getAmount());
        history.setCreatedAt(LocalDateTime.now());

        historyRepository.save(history);

        return AccountMapper.mapToAccountResponseDto(saveAccount);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountResponseDto(account))
                .collect(Collectors.toList());


    }

    @Override
    public void deleteAccountById(Long id) {
        if(!accountRepository.existsById(id)) throw new AccountNotExistException("Account not Found!", id);

         accountRepository.deleteById(id);
    }
}


