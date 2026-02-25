package com.example.demo.service;

import com.example.demo.dto.history.OperationHistoryDto;

import java.util.List;

public interface OperationHistoryService {

    List<OperationHistoryDto> getHistoryByAccountId(Long accountId);

    OperationHistoryDto assignType(Long operationId, Long typeId);
}
