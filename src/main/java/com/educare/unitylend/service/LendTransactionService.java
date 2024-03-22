package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.LendTransaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing Lend Transactions within the system
 */
public interface LendTransactionService {

    LendTransaction getLendTransactionInfo(String transactionId) throws ServiceException;

    List<LendTransaction> getLendTransactionsByUserId(String userId) throws ServiceException;

//    List<LendTransaction> getTransactionsBetweenPayerAndPayee(String payerId, String payeeId) throws ServiceException;

    List<LendTransaction> getAllLendTransactions() throws ServiceException;

    Boolean initiateLendTransaction(String lenderId, String borrowRequestId, BigDecimal amount) throws ServiceException;
}
