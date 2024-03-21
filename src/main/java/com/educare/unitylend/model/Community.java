package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a community
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private String communityId;
    private String communityName;
    private String communityTag;
    private String communityDetail;
}
