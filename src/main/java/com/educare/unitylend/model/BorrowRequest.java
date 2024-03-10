package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    private String requestId;
    private User borrower;
    private Community community;
    private String returnPeriod;
    private String status;
    private LocalDateTime timestamp;
    private BigDecimal collectedAmount;
    private BigDecimal targetAmount;
}
