package com.educare.unitylend.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents wallet associated with each user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private String walletId;
    private User user;
    private BigDecimal balance;
}