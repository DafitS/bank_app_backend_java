package com.example.demo.service.impl;

import com.example.demo.dto.type.ExpenseCreateDto;
import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.entity.ExpenseType;
import com.example.demo.exceptions.custom.DuplicateError;
import com.example.demo.exceptions.custom.TypeNotFound;
import com.example.demo.mapper.ExpenseMapper;
import com.example.demo.repository.ExpenseTypeRepository;
import com.example.demo.service.ExpenseTypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeServiceImpl(ExpenseTypeRepository operationExpenseRepository) {
        this.expenseTypeRepository = operationExpenseRepository;
    }

    @Override
    public ExpenseResponseDto createOperationExpenseType(ExpenseCreateDto expenseCreateDto) {
        String normalized = expenseCreateDto.getName().trim().toUpperCase();

        if (expenseTypeRepository.findByName(normalized).isPresent()) {
            throw new DuplicateError("Expense type already exists!");
        }

        ExpenseType typeNew = new ExpenseType();
        typeNew.setName(normalized);
        typeNew.setActive(true);

        return ExpenseMapper.mapToOperationExpenseResponseDto(expenseTypeRepository.save(typeNew));

    }

    @Override
    public List<ExpenseResponseDto> getAll() {
        return expenseTypeRepository
                .findAll()
                .stream()
                .map(ExpenseMapper::mapToOperationExpenseResponseDto)
                .toList();
    }

    @Override
    public ExpenseType findById(Long id) {
        return expenseTypeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFound("Expense type not found!"));
    }
}
