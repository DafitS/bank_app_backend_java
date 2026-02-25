package com.example.demo.mapper;

import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.entity.ExpenseType;

public class ExpenseMapper {

    public static ExpenseResponseDto mapToOperationExpenseResponseDto(ExpenseType expenseType) {

        return ExpenseResponseDto.builder()
                .id(expenseType.getId())
                .name(expenseType.getName())
                .active(expenseType.isActive())
                .build();
    }
}