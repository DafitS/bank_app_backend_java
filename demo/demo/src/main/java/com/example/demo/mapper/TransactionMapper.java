package com.example.demo.mapper;

import com.example.demo.dto.transaction.TransactionCreateDto;
import com.example.demo.dto.transaction.TransactionResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionCreateDto dto, Account senderAccount)
    {
        Transaction transaction = new Transaction();
        transaction.setAccountFrom(senderAccount);
        transaction.setAccountTo(dto.getAccountTo());
        transaction.setAmount(dto.getAmount());
        return transaction;
    }


    public static TransactionResponseDto mapToTransactionResponseDto(Transaction transaction)
    {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAccountFrom() != null ? transaction.getAccountFrom().getAccountNumber() : null,
                transaction.getAccountTo(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );
    }
}

