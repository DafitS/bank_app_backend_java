package com.example.demo.service.impl;

import com.example.demo.dto.OperationHistoryDto;
import com.example.demo.entity.OperationHistory;
import com.example.demo.mapper.OperationHistoryMapper;
import com.example.demo.repository.OperationHistoryRepository;
import com.example.demo.service.OperationHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationHistoryServiceImpl implements OperationHistoryService{

    private final OperationHistoryRepository historyRepository;

    public OperationHistoryServiceImpl(OperationHistoryRepository historyRepository)
    {
        this.historyRepository = historyRepository;
    }


    @Override
    public List<OperationHistoryDto> getHistoryByAccountId(Long accountId) {
        List<OperationHistory> histories = historyRepository.findByAccountIdOrderByCreatedAtDesc(accountId);

        return histories.stream().map((history) -> OperationHistoryMapper.mapToOperationHistoryDto(history))
                .collect(Collectors.toList());
    }
}
