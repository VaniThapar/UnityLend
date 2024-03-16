package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Boolean initiateTransaction(Transaction transaction) throws ServiceException;
    List<Transaction> getTransactionsBySender(String senderId) throws ServiceException;
    List<Transaction> getTransactionsBySenderToReceiver(String senderId, String receiverId) throws ServiceException;
    List<Transaction> getTransactionsOfSenderByDateRange(String senderId, LocalDate startDate, LocalDate endDate) throws ServiceException;
    List<Transaction> getAllTransactions() throws ServiceException;

}
