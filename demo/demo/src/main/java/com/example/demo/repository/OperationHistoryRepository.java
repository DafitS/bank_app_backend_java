package com.example.demo.repository;

import com.example.demo.dto.utility.SummaryDto;
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

    @Query("""
    SELECT new com.example.demo.dto.utility.SummaryDto(
        SUM(CASE WHEN o.operationType IN :incomeTypes THEN o.amount ELSE 0 END),
        SUM(CASE WHEN o.operationType IN :expenseTypes THEN o.amount ELSE 0 END)
    )
    FROM OperationHistory o
    WHERE o.account.id = :accountId
      AND o.createdAt BETWEEN :dateFrom AND :dateTo
""")
    SummaryDto getSummary(
            @Param("accountId") Long accountId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("incomeTypes") List<OperationType> incomeTypes,
            @Param("expenseTypes") List<OperationType> expenseTypes
    );

    List<OperationHistory> findAllByAccountIdAndCreatedAtBetween(
            Long accountId,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    );
}
