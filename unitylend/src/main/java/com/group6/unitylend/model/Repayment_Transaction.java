package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Repayment_Transaction")
public class Repayment_Transaction {
    @Id
    @Column(name = "RepayTransactionId ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID RepayTransactionId;

    @JoinColumn(name = "PayerId", referencedColumnName = "UserId")
    private UUID PayerId;

    @JoinColumn(name = "PayeeId", referencedColumnName = "UserId")
    private UUID PayeeId;

    @JoinColumn(name = "RequestId", referencedColumnName = "RequestId")
    private UUID RequestId;

    @Column(name = "Amount", precision = 10, scale = 2, nullable = false)
    private Float Amount;

    @Column(name = "Timestamp", nullable = false)
    private Date Timestamp;

    public UUID getRepayTransactionId() {
        return RepayTransactionId;
    }

    public void setRepayTransactionId(UUID repayTransactionId) {
        RepayTransactionId = repayTransactionId;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
