package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID userID;
    private String password;
    private String name;
    private String email;
    private Date dob;
    private Integer income;
    private String locality;
    private String college;
    private String company;
    private UUID walletID;
}
