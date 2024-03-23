package com.educare.unitylend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the Status of an activity (borrow request, transaction,etc.)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private Integer statusCode;
    private String statusName;
}
