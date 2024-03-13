package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.LendingTransactionRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.LendingTransaction;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.LendingTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class LendingTransactionServiceImpl implements LendingTransactionService {

    UserRepository userRepository;
    LendingTransactionRepository lendingTransactionRepository;


    @Override
    public LendingTransaction getTransactionInfo(String transactionId) throws ServiceException {
        try {
            LendingTransaction transaction = lendingTransactionRepository.getTransactionById(transactionId);
            String lenderId = lendingTransactionRepository.getLenderId(transactionId);
            String requestId = lendingTransactionRepository.getRequestId(transactionId);

            User lender = userRepository.getUserForUserId(lenderId);
//                BorrowRequest request = borrowRequestRepository.getBorrowRequest(transactionId);
            transaction.setLender(lender);
//            transaction.setBorrowRequest(request);
            log.info("Transaction " + transaction);
            return transaction;
        } catch (Exception e) {
            log.error("Error encountered while fetching transaction with given id");
            throw new ServiceException("Error encountered while fetching transaction with given id", e);
        }
    }

    @Override
    public List<LendingTransaction> getTransactionsByUserId(String userId) throws ServiceException {
        try {
            List<LendingTransaction> transactions = lendingTransactionRepository.getTransactionByUserId(userId);
            log.info("Transaction list " + transactions);
            for (LendingTransaction transaction : transactions) {
                String transactionId = transaction.getLendTransactionId();
                String lenderId = lendingTransactionRepository.getLenderId(transactionId);
                String requestId = lendingTransactionRepository.getRequestId(transactionId);

                User lender = userRepository.getUserForUserId(lenderId);
//                BorrowRequest request = borrowRequestRepository.getBorrowRequest(transactionId);
                transaction.setLender(lender);
//                transaction.setBorrowRequest(request);
            }
            return transactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions");
            throw new ServiceException("Error encountered during fetching transactions", e);
        }
    }

    @Override
    public List<LendingTransaction> getTransactionsBetweenPayerAndPayee(String payerId, String payeeId) throws ServiceException {
        try {
            List<LendingTransaction> transactions = lendingTransactionRepository.getTransactionsBetweenPayerAndPayee(payerId, payeeId);
            log.info("Transaction list " + transactions);
            for (LendingTransaction transaction : transactions) {
                String transactionId = transaction.getLendTransactionId();
                String lenderId = lendingTransactionRepository.getLenderId(transactionId);
                String requestId = lendingTransactionRepository.getRequestId(transactionId);

                User lender = userRepository.getUserForUserId(lenderId);
//                BorrowRequest request = borrowRequestRepository.getBorrowRequest(transactionId);
                transaction.setLender(lender);
//                transaction.setBorrowRequest(request);
            }
            return transactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions between payer and payee");
            throw new ServiceException("Error encountered during fetching transactions between payer and payee", e);
        }
    }
}
