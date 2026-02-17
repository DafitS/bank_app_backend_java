package com.example.demo.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Long id;

    private String accountFrom;
    private String accountTo;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}