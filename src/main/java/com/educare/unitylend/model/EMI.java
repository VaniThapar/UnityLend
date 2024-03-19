package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EMI {
    private String emiId;
    private BigDecimal emiAmount;
    private LocalDateTime emiDate;
    private BorrowRequest borrowRequest;
    private Status emiStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
