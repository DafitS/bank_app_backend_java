package com.example.demo.service.impl;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.OperationHistory;
import com.example.demo.entity.User;
import com.example.demo.exceptions.custom.*;
import com.example.demo.exceptions.custom.account.AccountNotActiveException;
import com.example.demo.exceptions.custom.account.AccountNotExistException;
import com.example.demo.exceptions.custom.address.UserNotExistException;
import com.example.demo.mapper.AccountsMapper;
import com.example.demo.option.OperationType;
import com.example.demo.option.RoleType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OperationHistoryRepository historyRepository;
    private final AccountsMapper accountsMapper;

    @Override
    public AccountResponseDto createAccount(AccountCreateDto accountCreateDto) {
        validate(accountCreateDto);
        User user = userRepository
                .findById(accountCreateDto.getUserId())
                .orElseThrow(()->new UserNotExistException("User not found!", accountCreateDto.getAccountHolderName()));

        Account account = accountsMapper.toEntity(accountCreateDto, user);
        Account savedAccount = accountRepository.save(account);

        return accountsMapper.toDto(savedAccount);
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
        if (!accountDto.getAccountNumber().matches("\\d{26}")) {
            throw new InvalidArgumentException("Account number must contain exactly 26 digits");
        }
    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountNotExistException("Account not Found!", id));

        return accountsMapper.toDto(account);
    }

    @Transactional
    @Override
    public AccountResponseDto deposit(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Deposit amount must be greater than zero");
        }
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountNotExistException("Account not Found!", id));

        if(!account.isActive())
        {
            throw new AccountNotActiveException("Account is Disabled!");
        }

        User currentUser = getCurrentUser();

        if (!account.getUser().getId().equals(currentUser.getId())
                && currentUser.getRoleType() != RoleType.ADMINISTRATOR) {
            throw new AccessDeniedException("You cannot deposit from this account");
        }

        account.setAmount(account.getAmount().add(amount));
        Account saveAccount = accountRepository.save(account);

        OperationHistory history = new OperationHistory();
        history.setAccount(saveAccount);
        history.setOperationType(OperationType.DEPOSIT);
        history.setAmount(amount);
        history.setBalanceAfter(saveAccount.getAmount());
        history.setCreatedAt(LocalDateTime.now());

        historyRepository.save(history);

        return accountsMapper.toDto(saveAccount);
    }

    @Transactional
    @Override
    public AccountResponseDto withdraw(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Withdraw amount must be greater than zero");
        }
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountNotExistException("Account not Found!", id));

        if(!account.isActive())
        {
            throw new AccountNotActiveException("Account is Disabled!");
        }
        User currentUser = getCurrentUser();

        if (!account.getUser().getId().equals(currentUser.getId())
                && currentUser.getRoleType() != RoleType.ADMINISTRATOR) {
            throw new AccessDeniedException("You cannot withdraw from this account");
        }

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

        return accountsMapper.toDto(saveAccount);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountsMapper::toDto)
                .collect(Collectors.toList());


    }

    @Override
    public void closeAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotExistException("Account not Found!", id));

        if(account.getAmount().compareTo(BigDecimal.ZERO) > 0)
        {
            throw new InvalidArgumentException("Cannot close account with balance > 0");
        }

        account.setActive(false);
        account.setClosedAt(LocalDateTime.now());
        accountRepository.save(account);


    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}


