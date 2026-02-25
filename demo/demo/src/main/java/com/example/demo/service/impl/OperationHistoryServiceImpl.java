package com.example.demo.service.impl;

import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.entity.ExpenseType;
import com.example.demo.entity.OperationHistory;
import com.example.demo.mapper.OperationHistoryMapper;
import com.example.demo.repository.ExpenseTypeRepository;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.service.OperationHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationHistoryServiceImpl implements OperationHistoryService{

    private final OperationHistoryRepository historyRepository;
    private final ExpenseTypeRepository expenseTypeRepository;

    public OperationHistoryServiceImpl(OperationHistoryRepository historyRepository, ExpenseTypeRepository expenseTypeRepository)
    {
        this.historyRepository = historyRepository;
        this.expenseTypeRepository = expenseTypeRepository;
    }


    @Override
    public List<OperationHistoryDto> getHistoryByAccountId(Long accountId) {
        List<OperationHistory> histories = historyRepository.findByAccountIdOrderByCreatedAtDesc(accountId);

        return histories.stream().map((history) -> OperationHistoryMapper.mapToOperationHistoryDto(history))
                .collect(Collectors.toList());
    }

    @Override
    public OperationHistoryDto assignType(Long operationId, Long typeId) {
        OperationHistory operation = historyRepository.findById(operationId)
                .orElseThrow(() -> new RuntimeException("Operation not found"));

        ExpenseType type = expenseTypeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Expense type not found"));

        operation.setExpenseType(type);
        historyRepository.save(operation);

        return OperationHistoryMapper.mapToOperationHistoryDto(operation);
    }
}
