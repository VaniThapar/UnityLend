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
}

