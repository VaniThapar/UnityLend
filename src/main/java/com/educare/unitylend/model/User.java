package com.educare.unitylend.model;

import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Getter @Setter private String userid;
    @Getter @Setter private String password;
    @Getter @Setter private String name;
    @Getter @Setter private String email;
    @Getter @Setter private LocalDate dob;
    @Getter @Setter private Integer income;
    @Getter @Setter private Integer borrowingLimit;
    @Getter @Setter private String officename;
    @Getter @Setter private String collegeuniversity;
    @Getter @Setter private String locality;

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

    public String getUserid() {
        return userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setIncome(Integer income) {
        // System.out.println(income);
        this.income = income;
    }

    public Integer getBorrowingLimit() {
        return borrowingLimit;
    }

    public void setBorrowingLimit(Integer borrowingLimit) {
        this.borrowingLimit = borrowingLimit;
        //   System.out.println(borrowingLimit);
    }


}
