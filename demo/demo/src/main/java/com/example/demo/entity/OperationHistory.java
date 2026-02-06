package com.example.demo.entity;

import com.example.demo.option.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="operation_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private BigDecimal amount;

    private BigDecimal balanceAfter;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    private String relatedAccountNumber;
}
