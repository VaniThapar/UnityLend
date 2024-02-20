package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userID;

    @Column(name = "Password ")
    @NonNull
    private final String password;

    @Column(name = "Email")
    @NonNull
    private final String name;

    @Column(name = "Email")
    @NonNull
    private String email;

    @Column
    @NonNull
    private Date dob;

    @Column
    @NonNull
    private Integer income;

    @Column
    private String locality;

    @Column
    private String college;

    @Column
    private String company;

    @Column
    private UUID walletID;

    public User(@NonNull String password, @NonNull String name) {
        this.password = password;
        this.name = name;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public Date getDob() {
        return dob;
    }

    public void setDob(@NonNull Date dob) {
        this.dob = dob;
    }

    @NonNull
    public Integer getIncome() {
        return income;
    }

    public void setIncome(@NonNull Integer income) {
        this.income = income;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public UUID getWalletID() {
        return walletID;
    }

    public void setWalletID(UUID walletID) {
        this.walletID = walletID;
    }

    @PostPersist
    public void createWallet() {
        UUID currentUserId = this.getUserID();
        Wallet currentUserWallet = new Wallet(currentUserId, (float)0);
        setWalletID(currentUserWallet.getWalletId());
    }
}
