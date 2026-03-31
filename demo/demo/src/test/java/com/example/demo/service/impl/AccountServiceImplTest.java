package com.example.demo.service.impl;

import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.exceptions.custom.account.AccountNotActiveException;
import com.example.demo.exceptions.custom.account.AccountNotExistException;
import com.example.demo.exceptions.custom.InvalidArgumentException;
import com.example.demo.option.RoleType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEachExecute");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("@AfterEachExecute");
    }

    @Test
    public void shouldThrowExceptionWhenAccountNotFound() {
        //Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository operationHistoryRepository = mock(OperationHistoryRepository.class);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository, operationHistoryRepository);

        //When
        AccountNotExistException accountNotExistException = assertThrows(AccountNotExistException.class, () -> accountService.getAccountById(1L));

        //Then
        assertTrue(accountNotExistException.getMessage().contains("Account not Found!"));
        assertEquals("Account not Found!", accountNotExistException.getMessage());
    }

    @Test
    public void shouldReturnAccountDtoWhenAccountExists() {
        //Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository operationHistoryRepository = mock(OperationHistoryRepository.class);
        Account account = new Account();
        account.setAccountNumber("12345678912345678912345");
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository, operationHistoryRepository);

        //When
        AccountResponseDto accountById = accountService.getAccountById(1L);

        //Then
        assertNotNull(accountById);
        assertEquals("12345678912345678912345", accountById.getAccountNumber());
        verify(accountRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldDepositMoney() {

        //Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository historyRepository = mock(OperationHistoryRepository.class);

        AccountServiceImpl accountService =
                new AccountServiceImpl(accountRepository, userRepository, historyRepository);

        User user = new User();
        user.setId(1L);
        user.setRoleType(RoleType.STANDARD_USER);

        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setActive(true);
        account.setAmount(new BigDecimal("100"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        AccountResponseDto result =
                accountService.deposit(1L, new BigDecimal("50"));

        //Then
        assertEquals(new BigDecimal("150"), account.getAmount());
        verify(accountRepository).save(account);
        verify(historyRepository).save(any());
    }

    @Test
    void shouldThrowWhenAmountIsNullOrNegative() {

        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository historyRepository = mock(OperationHistoryRepository.class);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository, historyRepository);

        User user = new User();
        user.setId(1L);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Account account = new Account();
        account.setUser(user);
        account.setActive(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(InvalidArgumentException.class,
                () -> accountService.deposit(1L, null));

        assertThrows(InvalidArgumentException.class,
                () -> accountService.deposit(1L, BigDecimal.ZERO));

        assertThrows(InvalidArgumentException.class,
                () -> accountService.deposit(1L, new BigDecimal("-10")));
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository historyRepository = mock(OperationHistoryRepository.class);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository, historyRepository);

        User user = new User();
        user.setId(1L);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotExistException.class,
                () -> accountService.deposit(1L, new BigDecimal("100")));
    }

    @Test
    void shouldThrowWhenAccountNotActive() {
        //Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        OperationHistoryRepository historyRepository = mock(OperationHistoryRepository.class);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository, historyRepository);

        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setUser(user);
        account.setActive(false);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        //When
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        //Then
        assertThrows(AccountNotActiveException.class,
                () -> accountService.deposit(1L, new BigDecimal("100")));

        verify(accountRepository, times(1)).findById(eq(1L));
    }

}