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

    public String getBorrowRequestId() {
        return borrowRequestId;
    }

    public void setBorrowRequestId(String borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Integer getReturnPeriodDays() {
        return returnPeriodDays;
    }

    public void setReturnPeriodDays(Integer returnPeriodDays) {
        this.returnPeriodDays = returnPeriodDays;
    }

    public BigDecimal getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    public void setMonthlyInterestRate(BigDecimal monthlyInterestRate) {
        this.monthlyInterestRate = monthlyInterestRate;
    }

    public Status getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Status borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(BigDecimal collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public BigDecimal getDefaultFine() {
        return defaultFine;
    }

    public void setDefaultFine(BigDecimal defaultFine) {
        this.defaultFine = defaultFine;
    }

    public Integer getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(Integer defaultCount) {
        this.defaultCount = defaultCount;
    }

//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getLastModifiedAt() {
//        return lastModifiedAt;
//    }
//
//    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
//        this.lastModifiedAt = lastModifiedAt;
//    }

    public List<String> getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(List<String> communityIds) {
        this.communityIds = communityIds;
    }

    private Integer returnPeriodDays;
    private BigDecimal monthlyInterestRate;
    private Status borrowStatus;
    private BigDecimal requestedAmount;
    private BigDecimal collectedAmount;
//  private Boolean isDefaulted;
    private BigDecimal defaultFine;
    private Integer defaultCount;
//  private LocalDateTime createdAt;
//  private LocalDateTime lastModifiedAt;
    private List<String> communityIds;
}
