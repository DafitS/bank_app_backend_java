package com.example.demo.controller;

import com.example.demo.dto.TransactionDto;
import com.example.demo.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/transaction")

public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto)
    {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id)
    {
        TransactionDto transactionDto = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDto);
    }
}
