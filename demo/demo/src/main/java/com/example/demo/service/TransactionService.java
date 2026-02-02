package com.example.demo.service;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);
}
