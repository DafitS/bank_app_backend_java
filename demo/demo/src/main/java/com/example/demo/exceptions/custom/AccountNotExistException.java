package com.example.demo.exceptions.custom;

public class AccountNotExistException extends ValidationException {
    public AccountNotExistException(String message, Long accountId)
    {
        super(message);
        this.accountId=accountId;
    }
    private Long accountId;

}

