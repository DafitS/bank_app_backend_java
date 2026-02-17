package com.example.demo.mapper;

import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.entity.OperationHistory;

public class OperationHistoryMapper {

    public static OperationHistoryDto mapToOperationHistoryDto(OperationHistory history)
    {
        OperationHistoryDto dto = new OperationHistoryDto();
        dto.setOperationType(history.getOperationType());
        dto.setAmount(history.getAmount());
        dto.setBalanceAfter(history.getBalanceAfter());
        dto.setCreatedAt(history.getCreatedAt());
        dto.setRelatedAccountNumber(history.getRelatedAccountNumber());
        return dto;
    }
}
