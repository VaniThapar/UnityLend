package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class LendingHistory {

    @Entity
    @Table(name = "Lend_Transaction")
    public class LendTransaction {

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")

        @Column(name = "LendTransactionId")
        private UUID lendTransactionId;

        @ManyToOne
        @JoinColumn(name = "LenderId", nullable = false)
        private User lender;

        @ManyToOne
        @JoinColumn(name = "BorrowerId", nullable = false)
        private User borrower;

        @ManyToOne
        @JoinColumn(name = "RequestId", nullable = false)
        private BorrowRequest borrowRequest;

        @Column(name = "Amount")
        private BigDecimal amount;

        @Column(name = "Timestamp")
        private LocalDateTime timestamp;
    }

}