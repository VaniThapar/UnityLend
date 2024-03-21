package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing the EMI payments within the system
 */
public interface EMIService {
    BigDecimal calculateBorrowEMIAmount(String borrowRequestId) throws ServiceException;

    Boolean addEMIDetails(Integer returnPeriod, String borrowRequestId) throws ServiceException;

    BigDecimal calculateLenderEMIAmount(String borrowRequestId, String lenderId) throws ServiceException;

}