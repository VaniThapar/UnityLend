package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public Boolean initiateTransaction(Transaction transaction) throws ServiceException {
        return false;
    }

    @Override
    public List<Transaction> getTransactionsBySender(String senderId) throws ServiceException {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsBySenderToReceiver(String senderId, String receiverId) throws ServiceException {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsOfSenderByDateRange(String senderId, LocalDate startDate, LocalDate endDate) throws ServiceException {
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() throws ServiceException {
        return null;
    }
}
