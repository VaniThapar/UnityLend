package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repayment_Transaction {
    private String RepayTransactionId;
    private String PayerId;
    private String PayeeId;
    private String RequestId;
    private Float Amount;
    private Date Timestamp;
}
