package com.example.demo.repository;

import com.example.demo.entity.OperationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationHistoryRepository extends JpaRepository<OperationHistory, Long> {
    List<OperationHistory> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
