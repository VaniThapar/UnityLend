package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID userid;
    private String password;
    private String name;
    private String email;
    private LocalDate dob;
    private Integer income;
    private Integer borrowingLimit;
    private String officename;
    private String collegeuniversity;
    private String locality;

    public Integer getIncome() {
        return income;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public String getCollegeuniversity() {
        return collegeuniversity;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setCollegeuniversity(String collegeuniversity) {
        this.collegeuniversity = collegeuniversity;
    }

    public UUID getUserid() {
        return userid;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setIncome(Integer income) {
        System.out.println(income);
        this.income = income;
    }

    public Integer getBorrowingLimit() {
        return borrowingLimit;
    }

    public void setBorrowingLimit(Integer borrowingLimit) {
        this.borrowingLimit = borrowingLimit;
        System.out.println(borrowingLimit);
    }


}
