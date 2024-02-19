package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class User {
    @Id
    @Column(name = "UserId")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID userId;

    @Column(name = "Password")
    @NotNull
    private String password;

    @Column(name = "Name")
    @NotNull
    private String name;

    @Column(name = "Email")
    @NotNull
    private String email;

    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateOfBirth;

    @Column(name = "Income")
    @NotNull
    private Double income;

    @Column(name = "Locality")
    private String locality;

    @Column(name = "College")
    private String college;

    @Column(name = "School")
    private String school;

    @Column(name = "Company")
    private String company;

    public UUID getUserId() {
        return userId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
