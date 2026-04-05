package com.example.demo.service.impl;

import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.dto.utility.RaportResponseDto;
import com.example.demo.dto.utility.SummaryDto;
import com.example.demo.entity.OperationHistory;
import com.example.demo.exceptions.custom.account.AccountNotExistException;
import com.example.demo.option.OperationType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.service.RaportService;
import com.example.demo.service.report.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RaportServiceImpl implements RaportService {

    private final OperationHistoryRepository operationHistoryRepository;
    private  final AccountRepository accountRepository;

    private ExpenseResponseDto mapExpense(OperationHistory op) {
        if (op.getExpenseType() == null) return null;

        return new ExpenseResponseDto(
                op.getExpenseType().getId(),
                op.getExpenseType().getName(),
                op.getExpenseType().isActive()
        );
    }

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

        SummaryDto summary = operationHistoryRepository.getSummary(accountId, dateFrom, dateTo, incomeTypes, expenseTypes);

        BigDecimal income = summary.getIncome() != null ? summary.getIncome() : BigDecimal.ZERO;
        BigDecimal expenses = summary.getExpenses() != null ? summary.getExpenses() : BigDecimal.ZERO;
        BigDecimal balance = income.subtract(expenses);

        List<OperationHistory> operations = operationHistoryRepository.findAllByAccountIdAndCreatedAtBetween(accountId, dateFrom, dateTo);

        List<OperationHistoryDto> operationsDto = operations.stream()
                .map(op -> new OperationHistoryDto(
                        op.getOperationType(),
                        op.getAmount(),
                        op.getBalanceAfter(),
                        op.getCreatedAt(),
                        op.getRelatedAccountNumber(),
                        mapExpense(op)


                ))
                .toList();

        RaportResponseDto responseDto = new RaportResponseDto(income, expenses, balance, operationsDto);

        FileService.generateReportPdf(responseDto, "raport_finansowy.pdf");
        return  responseDto;
    }

}
