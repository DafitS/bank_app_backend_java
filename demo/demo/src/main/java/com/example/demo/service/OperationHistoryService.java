package com.example.demo.service;

import com.example.demo.dto.OperationHistoryDto;

import java.util.List;

public interface OperationHistoryService {

    List<OperationHistoryDto> getHistoryByAccountId(Long accountId);
}
