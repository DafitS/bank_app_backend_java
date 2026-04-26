package com.example.demo.mapper;


import com.example.demo.dto.transaction.TransactionCreateDto;
import com.example.demo.dto.transaction.TransactionResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountFrom", source = "senderAccount")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "amount", source = "transactionCreateDto.amount")
    Transaction toEntity(TransactionCreateDto transactionCreateDto, Account senderAccount);

    @Mapping(target = "accountFrom", source = "accountFrom.accountNumber")
    TransactionResponseDto toDto(Transaction transaction);
}
