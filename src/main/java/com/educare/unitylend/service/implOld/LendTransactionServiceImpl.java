package com.educare.unitylend.service.implOld;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.LendTransactionRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.LendTransaction;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.LendTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class LendTransactionServiceImpl implements LendTransactionService {

    private UserRepository userRepository;
    private LendTransactionRepository lendingTransactionRepository;
    private BorrowRequestRepository borrowRequestRepository;

    @Override
    public LendTransaction getTransactionInfo(String transactionId) throws ServiceException {
        try {
            LendTransaction transaction = lendingTransactionRepository.getTransactionById(transactionId);
            String lenderId = lendingTransactionRepository.getLenderId(transactionId);
            String requestId = lendingTransactionRepository.getRequestId(transactionId);

            User lender = userRepository.getUserForUserId(lenderId);
            BorrowRequest request = borrowRequestRepository.getBorrowRequestByRequestId(requestId);
            transaction.setLender(lender);
            transaction.setBorrowRequest(request);
            log.info("Transaction " + transaction);
            return transaction;
        } catch (Exception e) {
            log.error("Error encountered while fetching transaction with given id");
            throw new ServiceException("Error encountered while fetching transaction with given id", e);
        }
    }

    @Override
    public List<LendTransaction> getTransactionsByUserId(String userId) throws ServiceException {
        try {
            List<LendTransaction> transactions = lendingTransactionRepository.getTransactionByUserId(userId);
            for (LendTransaction transaction : transactions) {
                String transactionId = transaction.getLendTransactionId();
                String lenderId = lendingTransactionRepository.getLenderId(transactionId);
                String requestId = lendingTransactionRepository.getRequestId(transactionId);

                User lender = userRepository.getUserForUserId(lenderId);
                BorrowRequest request = borrowRequestRepository.getBorrowRequestByRequestId(transactionId);
                transaction.setLender(lender);
                transaction.setBorrowRequest(request);
            }
            return transactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions");
            throw new ServiceException("Error encountered during fetching transactions", e);
        }
    }

    @Override
    public List<LendTransaction> getTransactionsBetweenPayerAndPayee(String payerId, String payeeId) throws ServiceException {
        try {
            List<LendTransaction> transactions = lendingTransactionRepository.getTransactionsBetweenPayerAndPayee(payerId, payeeId);
            log.info("Transaction list " + transactions);
            for (LendTransaction transaction : transactions) {
                String transactionId = transaction.getLendTransactionId();
                String lenderId = lendingTransactionRepository.getLenderId(transactionId);
                String requestId = lendingTransactionRepository.getRequestId(transactionId);

                User lender = userRepository.getUserForUserId(lenderId);
                BorrowRequest request = borrowRequestRepository.getBorrowRequestByRequestId(transactionId);
                transaction.setLender(lender);
                transaction.setBorrowRequest(request);
            }
            return transactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions between payer and payee");
            throw new ServiceException("Error encountered during fetching transactions between payer and payee", e);
        }
    }
}
