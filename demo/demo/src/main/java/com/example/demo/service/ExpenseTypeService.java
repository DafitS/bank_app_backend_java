package com.example.demo.service;

import com.example.demo.dto.type.ExpenseCreateDto;
import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.entity.ExpenseType;

import java.util.List;

public interface ExpenseTypeService {

    ExpenseResponseDto createOperationExpenseType (ExpenseCreateDto expenseCreateDto);

    List<ExpenseResponseDto> getAll();

    ExpenseType findById(Long id);
}
