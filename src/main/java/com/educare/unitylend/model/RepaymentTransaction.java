package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentTransaction {
    private String RepaymentTransactionId;
    private Transaction transaction;
    private BorrowRequest borrowRequest;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}

