package com.example.demo.repository;

import com.example.demo.entity.OperationHistory;
import com.example.demo.option.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface OperationHistoryRepository extends JpaRepository<OperationHistory, Long> {
    List<OperationHistory> findByAccountIdOrderByCreatedAtDesc(Long accountId);

    @Query
            ("""
            SELECT SUM(o.amount)
            FROM OperationHistory AS o
            WHERE o.account.id = :accountId
            AND o.operationType IN :types 
            AND o.createdAt  BETWEEN :dateFrom 
            AND :dateTo
            """)
    BigInteger countTotalSumInPeriod(
            @Param("accountId") Long accountId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("types")List<OperationType> types
    );
}
