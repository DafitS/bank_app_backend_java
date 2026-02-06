package com.example.demo.controller;


import com.example.demo.dto.OperationHistoryDto;
import com.example.demo.service.OperationHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/accounts")
public class OperationHistoryController {
    private OperationHistoryService operationHistoryService;

    public OperationHistoryController(OperationHistoryService operationHistoryService)
    {
        this.operationHistoryService = operationHistoryService;
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<OperationHistoryDto>> getAccountHistory(@PathVariable Long accountId)
    {
        List<OperationHistoryDto> historyDtoAccount = operationHistoryService.getHistoryByAccountId(accountId);

        return new ResponseEntity<>(historyDtoAccount, HttpStatus.OK);
    }

}
