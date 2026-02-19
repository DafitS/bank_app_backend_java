package com.example.demo.controller;

import com.example.demo.dto.transaction.TransactionCreateDto;
import com.example.demo.dto.transaction.TransactionResponseDto;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/transactions")

public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> addTransaction(@Valid @RequestBody TransactionCreateDto transactionCreateDto)
    {
        return new ResponseEntity<>(transactionService.createTransaction(transactionCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or @transactionSecurity.isOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id)
    {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}
