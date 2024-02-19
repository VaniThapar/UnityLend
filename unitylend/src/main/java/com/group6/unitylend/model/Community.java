package com.group6.unitylend.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity
@Table
public class Community {
    @Id
    @Column(name = "CommunityId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID communityId;

    @Column(name = "CommunityName")
    @NotNull
    private String communityName;


}
