package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.LendTransactionRepository;
import com.educare.unitylend.dao.TransactionRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.*;
import com.educare.unitylend.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class LendTransactionServiceImpl implements LendTransactionService {
    private LendTransactionRepository lendTransactionRepository;
    private BorrowRequestService borrowRequestService;
    private TransactionService transactionService;
    private UserService userService;
    private WalletService walletService;


    @Override
    public LendTransaction getLendTransactionInfo(String lendTransactionId) throws ServiceException {
        try {
            log.info("lend transaction info:: " + lendTransactionId);
            LendTransaction lendTransaction = lendTransactionRepository.getLendTransactionById(lendTransactionId);

            setParametersOfLendTransaction(lendTransaction);

            log.info("Lend Transaction:: " + lendTransaction);
            return lendTransaction;
        } catch (Exception e) {
            log.error("Error encountered while fetching transaction with given id");
            throw new ServiceException("Error encountered while fetching transaction with given id", e);
        }
    }

    @Override
    public List<LendTransaction> getLendTransactionsByUserId(String userId) throws ServiceException {
        try {
            List<LendTransaction> lendTransactions = lendTransactionRepository.getLendTransactionsByUserId(userId);
            setParametersOfLendTransaction(lendTransactions);

            log.info("Transaction list:: " + lendTransactions);
            return lendTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions");
            throw new ServiceException("Error encountered during fetching transactions", e);
        }
    }

//    @Override
//    public List<LendTransaction> getTransactionsBetweenPayerAndPayee(String payerId, String payeeId) throws ServiceException {
//        try {
//            List<LendTransaction> lendTransactions = lendTransactionRepository.getLendTransactionsBetweenPayerAndPayee(payerId, payeeId);
//            setParametersOfLendTransaction(lendTransactions);
//
//            log.info("Transaction list:: " + lendTransactions);
//            return lendTransactions;
//        } catch (Exception e) {
//            log.error("Error encountered during fetching transactions between payer and payee");
//            throw new ServiceException("Error encountered during fetching transactions between payer and payee", e);
//        }
//    }

    @Override
    public List<LendTransaction> getAllLendTransactions() throws ServiceException {
        try {
            List<LendTransaction> lendTransactions = lendTransactionRepository.getAllLendTransactions();

            setParametersOfLendTransaction(lendTransactions);

            log.info("Transaction list:: " + lendTransactions);
            return lendTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching all lend transactions");
            throw new ServiceException("Error encountered during fetching all lend transactions", e);
        }
    }

    @Override
    public Boolean initiateLendTransaction(String lenderId, String borrowRequestId, BigDecimal amount) throws ServiceException {
        try {

            Boolean isLendPossible=borrowRequestService.isLendAmountValid(borrowRequestId,amount);
            if(!isLendPossible){
                return false;
            }

            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
            User borrower = borrowRequest.getBorrower();
            String borrowerId = borrower.getUserId();

            log.info("borrow req:: " + borrowRequest);
            log.info("borrower:: " + borrower);
            log.info("borrower id:: " + borrowerId);

            Boolean isDebitedFromLender = handleDebitFromLender(lenderId, amount);
            Boolean isCreditedToBorrower = handleCreditToBorrower(borrowerId, amount);

            if (!isDebitedFromLender || !isCreditedToBorrower) {
                return false;
            }


            Boolean isInsertionInTransactionSuccessful = transactionService.initiateTransaction(lenderId, borrowerId, amount);
            String transactionId = transactionService.getLatestTranscationId();
            log.info("transaction id:: " + transactionId);
            Integer rowsAffectedInInsertionInLendTransactionSuccessful = lendTransactionRepository.insertLendTransaction(transactionId,
                    borrowRequestId);

            Boolean isBorrowRequestUpdated = borrowRequestService.updateCollectedAmount(borrowRequestId, amount);

            log.info("rowsAffectedInInsertionInLendTransactionSuccessful:: " + rowsAffectedInInsertionInLendTransactionSuccessful);
            log.info("isInsertionInTransactionSuccessful:: " + isInsertionInTransactionSuccessful);
            log.info("isBorrowRequestUpdated:: "+isBorrowRequestUpdated);

            return isInsertionInTransactionSuccessful && isBorrowRequestUpdated &&
                    rowsAffectedInInsertionInLendTransactionSuccessful > 0;
        } catch (ServiceException e) {
            log.error("Error encountered in initiating lend operation");
            throw new ServiceException("Error encountered in initiating lend operation", e);
        }
    }

    private Boolean handleDebitFromLender(String lenderId, BigDecimal amount) {
        try {
            log.info("lender id:: " + lenderId);
            Wallet wallet = walletService.getWalletForUserId(lenderId);
            String walletId = wallet.getWalletId();
            log.info("wallet id:: " + walletId);
            walletService.deductAmount(walletId, amount);
            return true;
        } catch (ServiceException e) {
            log.error("Error encountered in debiting money from lender's wallet");
            return false;
        }
    }

    private Boolean handleCreditToBorrower(String borrowerId, BigDecimal amount) {
        try {
            log.info("borrower id:: " + borrowerId);
            Wallet wallet = walletService.getWalletForUserId(borrowerId);
            String walletId = wallet.getWalletId();
            log.info("wallet id:: " + walletId);
            walletService.addAmount(walletId, amount);
            return true;
        } catch (ServiceException e) {
            log.error("Error encountered in crediting money to borrower's wallet");
            return false;

        }
    }

    private void setParametersOfLendTransaction(List<LendTransaction> lendTransactions) {
        try {
            for (LendTransaction lendTransaction : lendTransactions) {
                String lendTransactionId = lendTransaction.getLendTransactionId();
                String borrowRequestId = lendTransactionRepository.getBorrowRequestId(lendTransactionId);
                String transactionId = lendTransactionRepository.getTransactionId(lendTransactionId);
                log.info("lend transaction id:: " + lendTransactionId);
                log.info("borrow req id:: " + borrowRequestId);
                log.info("transaction id:: " + transactionId);

                BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
                Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);

                log.info("borr req:: " + borrowRequest);
                log.info("transaction:: " + transaction);

                lendTransaction.setBorrowRequest(borrowRequest);
                lendTransaction.setTransaction(transaction);
            }
        } catch (Exception e) {
            log.error("Error encountered while setting parameters of lend transaction");
        }
    }

    private void setParametersOfLendTransaction(LendTransaction lendTransaction) {
        try {
            String lendTransactionId = lendTransaction.getLendTransactionId();
            String borrowRequestId = lendTransactionRepository.getBorrowRequestId(lendTransactionId);
            String transactionId = lendTransactionRepository.getTransactionId(lendTransactionId);

            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
            Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);

            lendTransaction.setBorrowRequest(borrowRequest);
            lendTransaction.setTransaction(transaction);
        } catch (Exception e) {
            log.error("Error encountered while setting parameters of lend transaction");
        }
    }
}
