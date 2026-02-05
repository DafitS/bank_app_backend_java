package com.example.demo.exceptions;

public class AccountNotExistException extends ValidationException {
    public AccountNotExistException(String message, Long accountId)
    {
        super(message);
        this.accountId=accountId;
    }
    private Long accountId;

}

