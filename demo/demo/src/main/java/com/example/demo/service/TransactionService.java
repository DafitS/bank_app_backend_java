package com.example.demo.service;

import com.example.demo.dto.transaction.TransactionCreateDto;
import com.example.demo.dto.transaction.TransactionResponseDto;

public interface TransactionService {

    TransactionResponseDto createTransaction(TransactionCreateDto transactionCreateDto);

    TransactionResponseDto getTransactionById(Long id);
}
