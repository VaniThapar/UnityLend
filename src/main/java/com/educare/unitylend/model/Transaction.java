package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String transactionId;
    private User receiver;
    private User sender;
    private BigDecimal amount;
    private Status transactionStatus;
    private LocalDateTime lastUpdatedTime;
    private LocalDateTime transactionTime;


}
