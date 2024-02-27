package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private UUID communityid;
    private String communityname;
    private String commontag;


}
