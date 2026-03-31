package com.example.demo.service.impl;

import com.example.demo.dto.utility.RaportResponseDto;
import com.example.demo.exceptions.custom.account.AccountNotExistException;
import com.example.demo.option.OperationType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.service.RaportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigInteger;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RaportServiceImpl implements RaportService {

    private final OperationHistoryRepository operationHistoryRepository;
    private  final AccountRepository accountRepository;

    @Override
    public RaportResponseDto generateRaport(Long accountId, LocalDateTime dateFrom, LocalDateTime dateTo) {

        boolean isExist = accountRepository.existsById(accountId);
        if(!isExist)
        {
            throw new AccountNotExistException("Account not found.", accountId);
        }


        List<OperationType> expenseTypes = List.of(
                OperationType.WITHDRAW,
                OperationType.TRANSFER_OUT
        );

        List<OperationType> incomeTypes = List.of(
                OperationType.DEPOSIT,
                OperationType.TRANSFER_IN
        );

        BigInteger expenses = operationHistoryRepository
                .countTotalSumInPeriod(accountId, dateFrom, dateTo, expenseTypes);

        BigInteger income = operationHistoryRepository
                .countTotalSumInPeriod(accountId, dateFrom, dateTo, incomeTypes);

        expenses = expenses != null ? expenses : BigInteger.ZERO;
        income = income != null ? income : BigInteger.ZERO;

        BigInteger balance = income.subtract(expenses);

        return new RaportResponseDto(income, expenses, balance);

    }

}
