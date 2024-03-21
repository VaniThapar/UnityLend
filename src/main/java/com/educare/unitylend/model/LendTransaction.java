package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a Lend transaction
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class
LendTransaction {
    private String lendTransactionId;
    private Transaction transaction;
    private BorrowRequest borrowRequest;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}