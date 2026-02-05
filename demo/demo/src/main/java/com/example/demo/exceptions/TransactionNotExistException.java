package com.example.demo.exceptions;

public class TransactionNotExistException extends ValidationException {

    private final Long transactionId;

    public TransactionNotExistException(String message, Long transactionId) {
        super(message);
        this.transactionId = transactionId;
    }

}
