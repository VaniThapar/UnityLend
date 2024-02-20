package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Table(name = "Wallet")
@Entity
public class Wallet {
    @Id
    @Column(name = "Wallet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Wallet;

    @JoinColumn(name = "User", referencedColumnName = "userId")
    private UUID userId;

    @Column
    Float Balance;

    public Wallet(UUID userId, Float balance) {
        this.setBalance(balance);
        this.setUserId(userId);
    }

    public UUID getWalletId() {
        return Wallet;
    }

    public void setWalletId(UUID wallet) {
        Wallet = wallet;
    }

    public Float getBalance() {
        return Balance;
    }

    public void setBalance(Float balance) {
        Balance = balance;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
