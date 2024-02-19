package com.group6.unitylend.model;

import java.util.UUID;

public class user {
    private final UUID id;
    private final String password;
    private final String name;
    private String email;
    private String dob;
    private Integer income;
    private String locality;
    private String college;
    private String school;
    private String company;


    public user(UUID id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
