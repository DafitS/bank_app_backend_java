package com.example.demo.controller;


import com.example.demo.dto.history.OperationHistoryDto;
import com.example.demo.service.OperationHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE') or @accountSecurity.isOwner(#accountId)")
    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<OperationHistoryDto>> getAccountHistory(@PathVariable Long accountId)
    {
        List<OperationHistoryDto> historyDtoAccount = operationHistoryService.getHistoryByAccountId(accountId);

        return new ResponseEntity<>(historyDtoAccount, HttpStatus.OK);
    }

    @PutMapping("/{operationId}/assign-type/{typeId}")
    public ResponseEntity<OperationHistoryDto> assignType(
            @PathVariable Long operationId,
            @PathVariable Long typeId) {

        OperationHistoryDto dto = operationHistoryService.assignType(operationId, typeId);
        return ResponseEntity.ok(dto);
    }

}
