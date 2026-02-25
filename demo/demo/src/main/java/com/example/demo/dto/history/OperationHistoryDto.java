package com.example.demo.dto.history;

import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.option.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistoryDto {

    private OperationType operationType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;

    private String relatedAccountNumber;

    private ExpenseResponseDto expenseType;
}
