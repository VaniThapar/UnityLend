package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDate dob;
    private Integer income;
    private Integer borrowingLimit;
    private String officeName;
    private String collegeUniversity;
    private String locality;
    private boolean isActive;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getBorrowingLimit() {
        return borrowingLimit;
    }

    public void setBorrowingLimit(Integer borrowingLimit) {
        this.borrowingLimit = borrowingLimit;
    }

    public String getOfficenName() {
        return officeName;
    }

    public void setOfficename(String officeName) {
        this.officeName = officeName;
    }

    public String getCollegeuniversity() {
        return collegeUniversity;
    }

    public void setCollegeuniversity(String collegeUniversity) {
        this.collegeUniversity = collegeUniversity;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

