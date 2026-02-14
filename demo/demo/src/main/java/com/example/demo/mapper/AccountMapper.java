package com.example.demo.mapper;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;

public class AccountMapper {

    public static Account mapToAccount(AccountCreateDto dto, User user) {
        Account account = new Account();
        account.setId(null);
        account.setAccountHolderName(dto.getAccountHolderName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAmount(dto.getAmount());
        account.setUser(user);

        return account;
    }


    public static AccountResponseDto mapToAccountResponseDto(Account account) {
        return new AccountResponseDto(
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getAmount()

        );
    }
}
