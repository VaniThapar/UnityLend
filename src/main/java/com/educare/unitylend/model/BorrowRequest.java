package com.educare.unitylend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BorrowRequest {
    private String requestId;
    private User borrower;
    private List<String> communityIds;
    private Integer returnPeriod;
    private String status;
    private LocalDateTime timestamp;
    private BigDecimal collectedAmount;
    private BigDecimal requestedAmount;
    private BigDecimal monthlyInterestRate;

    public String getBorrowerId() {
        return (borrower != null) ? borrower.getUserId() : null;
    }


}
