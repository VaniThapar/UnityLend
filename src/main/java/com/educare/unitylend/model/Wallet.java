package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Getter @Setter private String walletid;
    @Getter @Setter private User user;
    @Getter @Setter private Float balance=0f;

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
