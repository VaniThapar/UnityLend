package com.educare.unitylend.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    @Getter @Setter
    private String requestId;

    @Getter @Setter
    private User borrower;

    @Getter @Setter
    private Community community;

    @Getter private String returnPeriod;

    @Getter @Setter
    private String status;

    @Getter @Setter
    private LocalDateTime timestamp;

    private BigDecimal collectedAmount;
    @Getter private BigDecimal targetAmount;

    public String getBorrowerId() {
        return borrower.getUserid();
    }
}
