package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;

import java.time.LocalDate;
import java.util.List;
public interface TransactionService {

    List<Transaction> getTransactionsBySender(User sender) throws ControllerException;

    List<Transaction> getTransactionsBetween(User sender, User receiver) throws ControllerException;

    List<Transaction> getAllTransactions() throws ControllerException;

    List<Transaction> getTransactionsByDate(LocalDate date) throws ControllerException;

    boolean initiateTransaction(Transaction transaction) throws ControllerException;

}
