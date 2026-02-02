package com.example.demo.mapper;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionDto dto)
    {
        return new Transaction(
                dto.getId(),
                dto.getAccountFrom(),
                dto.getAccountTo(),
                dto.getBalance(),
                dto.getTime()
        );
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction)
    {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountFrom(),
                transaction.getAccountTo(),
                transaction.getBalance(),
                transaction.getTime()
        );
    }
}
