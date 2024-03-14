package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ControllerException;
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
    public List<Transaction> getTransactionsBySender(User sender) throws ControllerException {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsBetween(User sender, User receiver) throws ControllerException {
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() throws ControllerException {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByDate(LocalDate date) throws ControllerException {
        return null;
    }

    @Override
    public boolean initiateTransaction(Transaction transaction) throws ControllerException {
        return false;
    }
}
