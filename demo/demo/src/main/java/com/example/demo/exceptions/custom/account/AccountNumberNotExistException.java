package com.example.demo.exceptions.custom.account;

public class AccountNumberNotExistException extends RuntimeException {

    private final String accountNumber;

    public AccountNumberNotExistException(String message, String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
