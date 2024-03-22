package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents a Repayment Transaction
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentTransaction {
    private String repaymentTransactionId;
    private Transaction transaction;
    private BorrowRequest borrowRequest;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}

