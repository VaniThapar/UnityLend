package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendingHistory {
    private UUID lendTransactionId;
    private User lender;
    private User borrower;
    private BorrowRequest borrowRequest;
    private BigDecimal amount;
    private LocalDateTime timestamp;

}