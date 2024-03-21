package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents the EMI that borrower has to pay
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EMI {
    private String emiId;
    private Integer emiNo;
    private BigDecimal emiAmount;
    private Date emiDate;
    private BorrowRequest borrowRequest;
    private Status emiStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
