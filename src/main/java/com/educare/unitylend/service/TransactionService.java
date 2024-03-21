package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.LendTransactionRepository;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing the Transactions occurring within the system
 */
public interface TransactionService {
    Boolean initiateTransaction(String senderId, String receiverId, BigDecimal amount) throws ServiceException;

    List<Transaction> getTransactionsBySender(String senderId) throws ServiceException;

    List<Transaction> getTransactionsBySenderToReceiver(String senderId, String receiverId) throws ServiceException;

    List<Transaction> getTransactionsOfSenderByDateRange(String senderId, LocalDate startDate, LocalDate endDate) throws ServiceException;

    List<Transaction> getAllTransactions() throws ServiceException;

    Transaction getTransactionByTransactionId(String transactionId) throws ServiceException;

    String getLatestTranscationId() throws ServiceException;
}
