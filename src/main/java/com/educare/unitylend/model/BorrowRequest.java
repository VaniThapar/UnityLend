package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a borrowing request made by a user.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    private String borrowRequestId;
    private User borrower;
    private Integer returnPeriodMonths;
    private BigDecimal monthlyInterestRate;
    private Status borrowStatus;
    private BigDecimal requestedAmount;
    private BigDecimal collectedAmount;
    private Boolean isDefaulted;
    private BigDecimal defaultFine;
    private Integer defaultCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<String> communityIds;
}
