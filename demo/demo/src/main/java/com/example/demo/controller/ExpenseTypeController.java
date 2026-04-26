package com.example.demo.controller;

import com.example.demo.dto.type.ExpenseCreateDto;
import com.example.demo.dto.type.ExpenseResponseDto;
import com.example.demo.entity.ExpenseType;
import com.example.demo.mapper.classes.ExpenseMapper;
import com.example.demo.service.ExpenseTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense-types")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> addType(@Valid @RequestBody ExpenseCreateDto expenseCreateDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseTypeService.createOperationExpenseType(expenseCreateDto));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE')")
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAll() {
        return ResponseEntity.ok(expenseTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getById(@PathVariable Long id) {
        ExpenseType expenseType = expenseTypeService.findById(id);
        ExpenseResponseDto dto = ExpenseMapper.mapToOperationExpenseResponseDto(expenseType);

        return ResponseEntity.ok(dto);
    }
}
