package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;
    private String contactNo;
    private LocalDate dob;
    private BigDecimal income;
    private Map<String,String> communityDetails;
    private String communityDetailsJson;
}

