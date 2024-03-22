package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.LendTransaction;
import com.educare.unitylend.model.RepaymentTransaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing the Repayment transactions within the system
 */
public interface RepaymentTransactionService {
    Boolean initiateRepaymentTransaction(String borrowerRequestId) throws ServiceException;

    Boolean repayDefaultEMI(String borrowerRequestId) throws ServiceException;

    List<RepaymentTransaction> getRepaymentTransactionsByUserId(String userId) throws ServiceException;

    RepaymentTransaction getRepaymentTransactionInfo(String repaymentTransactionId) throws ServiceException;

    List<RepaymentTransaction> getAllRepaymentTransactions() throws ServiceException;
}