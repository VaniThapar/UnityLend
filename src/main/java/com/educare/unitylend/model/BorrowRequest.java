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
    private String borrowRequestId;
    private User borrower;
    private Integer returnPeriodDays;
    private BigDecimal monthlyInterestRate;
    private Status borrowStatus;
    private BigDecimal requestedAmount;
    private BigDecimal collectedAmount;
    private Boolean isDefaulted;
    private BigDecimal defaultFine;
    private  Integer defaultCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<String> communityIds;
}
