package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class BorrowRequest {


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")

    @Column(name="RequestId")
    private UUID requestId;
    @ManyToOne
    @JoinColumn(name = "BorrowerID", nullable = false)
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "CommunityID", nullable = false)
    private Community community;

    @Column(name = "ReturnPeriod")
    private String returnPeriod;

    @Column(name = "Status")
    private String status;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    @Column(name = "CollectedAmount")
    private BigDecimal collectedAmount;

    @Column(name = "TargetAmount")
    private BigDecimal targetAmount;
}
