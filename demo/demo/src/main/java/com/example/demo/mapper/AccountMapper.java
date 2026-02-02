package com.example.demo.mapper;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;

public class AccountMapper {

    public static Account mapToAccount(AccountDto dto, User user) {
        Account account = new Account();
        account.setId(null);
        account.setAccountHolderName(dto.getAccountHolderName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAmount(dto.getAmount());
        account.setUser(user);

        return account;
    }


    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getAmount(),
                account.getUser().getId()
        );
    }
}
