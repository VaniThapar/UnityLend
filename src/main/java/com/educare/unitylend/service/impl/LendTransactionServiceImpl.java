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

    /**

     Retrieves information about a lend transaction based on the provided lend transaction ID.
     @param lendTransactionId The ID of the lend transaction to retrieve information for.
     @return The lend transaction object containing information about the transaction.
     @throws ServiceException if an error occurs during the retrieval process.
     */
    @Override
    public LendTransaction getLendTransactionInfo(String lendTransactionId) throws ServiceException {
        try {
            LendTransaction lendTransaction = lendTransactionRepository.getLendTransactionById(lendTransactionId);

            if(lendTransaction==null){
                throw new Exception("Lend transaction is null");
            }

            setParametersOfLendTransaction(lendTransaction);

            return lendTransaction;
        } catch (Exception e) {
            log.error("Error encountered while fetching transaction with given id");
            throw new ServiceException("Error encountered while fetching transaction with given id", e);
        }
    }


    /**
     * Retrieves a list of lend transactions associated with a given user ID.
     *
     * @param userId The ID of the user for which to retrieve lend transactions.
     * @return A list of lend transaction objects associated with the user.
     * @throws ServiceException if an error occurs during the retrieval process.
     */
    @Override
    public List<LendTransaction> getLendTransactionsByUserId(String userId) throws ServiceException {
        try {
            List<LendTransaction> lendTransactions = lendTransactionRepository.getLendTransactionsByUserId(userId);

            if(lendTransactions==null){
                throw new Exception("Lend transaction List is null");
            }

            setParametersOfLendTransaction(lendTransactions);

            return lendTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching transactions");
            throw new ServiceException("Error encountered during fetching transactions", e);
        }
    }


    /**
     * Retrieves a list of all lend transactions.
     *
     * @return A list of all lend transactions.
     * @throws ServiceException if an error occurs during the retrieval process.
     */
    @Override
    public List<LendTransaction> getAllLendTransactions() throws ServiceException {
        try {
            List<LendTransaction> lendTransactions = lendTransactionRepository.getAllLendTransactions();

            if(lendTransactions==null){
                throw new Exception("Lend transaction List is null");
            }

            setParametersOfLendTransaction(lendTransactions);

            return lendTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching all lend transactions");
            throw new ServiceException("Error encountered during fetching all lend transactions", e);
        }
    }

    /**
     * Initiates a lend transaction between a lender and a borrower for a specified amount.
     *
     * @param lenderId The ID of the lender initiating the transaction.
     * @param borrowRequestId The ID of the borrow request for which the transaction is initiated.
     * @param amount The amount to be lent in the transaction.
     * @return {@code true} if the lend transaction is successfully initiated; {@code false} otherwise.
     * @throws ServiceException if an error occurs during the initiation process.
     */
    @Override
    public Boolean initiateLendTransaction(String lenderId, String borrowRequestId, BigDecimal amount) throws ServiceException {
        try {
            if(amount.compareTo(BigDecimal.valueOf(0))<=0){
                log.error("Amount is less than zero");
                return false;
            }

            Boolean isLendPossible=borrowRequestService.isLendAmountValid(borrowRequestId,amount);
            if(!isLendPossible){
                log.error("Amount that is being lent is more than what is required");
                return false;
            }
            Boolean hasLent=lendTransactionRepository.hasLent(lenderId,borrowRequestId);
            if(hasLent){
                log.error("Cannot Lend! Given Lender has already lent to this borrow request.");
                return false;
            }

            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);

            if(borrowRequest==null){
                throw new Exception("Borrow request is null");
            }

            User borrower = borrowRequest.getBorrower();
            String borrowerId = borrower.getUserId();

            Boolean isDebitedFromLender = handleDebitFromLender(lenderId, amount);
            Boolean isCreditedToBorrower = handleCreditToBorrower(borrowerId, amount);

            if (!isDebitedFromLender || !isCreditedToBorrower) {
                return false;
            }

            Boolean isInsertionInTransactionSuccessful = transactionService.initiateTransaction(lenderId, borrowerId, amount);
            String transactionId = transactionService.getLatestTranscationId();
            Integer rowsAffectedInInsertionInLendTransactionSuccessful = lendTransactionRepository.insertLendTransaction(transactionId,
                    borrowRequestId);

            Boolean isBorrowRequestUpdated = borrowRequestService.updateCollectedAmount(borrowRequestId, amount);

            return isInsertionInTransactionSuccessful && isBorrowRequestUpdated &&
                    rowsAffectedInInsertionInLendTransactionSuccessful > 0;
        } catch (Exception e) {
            log.error("Error encountered in initiating lend operation");
            throw new ServiceException("Error encountered in initiating lend operation", e);
        }
    }


    /**
     * Handles the debit operation from the lender's wallet.
     *
     * @param lenderId The ID of the lender from whose wallet the amount is to be deducted.
     * @param amount The amount to be deducted from the lender's wallet.
     * @return {@code true} if the debit operation is successful; {@code false} otherwise.
     */
    private Boolean handleDebitFromLender(String lenderId, BigDecimal amount) {
        try {
            Wallet wallet = walletService.getWalletForUserId(lenderId);
            if(wallet==null){
                throw new Exception("Wallet is null");
            }
            String walletId = wallet.getWalletId();
            walletService.deductAmount(walletId, amount);
            return true;
        } catch (Exception e) {
            log.error("Error encountered in debiting money from lender's wallet");
            return false;
        }
    }


    /**
     * Handles the credit operation to the borrower's wallet.
     *
     * @param borrowerId The ID of the borrower to whose wallet the amount is to be credited.
     * @param amount The amount to be credited to the borrower's wallet.
     * @return {@code true} if the credit operation is successful; {@code false} otherwise.
     */
    private Boolean handleCreditToBorrower(String borrowerId, BigDecimal amount) {
        try {
            Wallet wallet = walletService.getWalletForUserId(borrowerId);
            if(wallet==null){
                throw new Exception("Wallet is null");
            }
            String walletId = wallet.getWalletId();
            walletService.addAmount(walletId, amount);
            return true;
        } catch (Exception e) {
            log.error("Error encountered in crediting money to borrower's wallet");
            return false;
        }
    }


    /**
     * Sets parameters of lend transactions by populating each lend transaction object with related
     * borrow request and transaction details.
     *
     * @param lendTransactions The list of lend transactions to set parameters for.
     */
    private void setParametersOfLendTransaction(List<LendTransaction> lendTransactions) {
        try {
            for (LendTransaction lendTransaction : lendTransactions) {
                String lendTransactionId = lendTransaction.getLendTransactionId();
                String borrowRequestId = lendTransactionRepository.getBorrowRequestId(lendTransactionId);
                String transactionId = lendTransactionRepository.getTransactionId(lendTransactionId);

                BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
                Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);

                lendTransaction.setBorrowRequest(borrowRequest);
                lendTransaction.setTransaction(transaction);
            }
        } catch (Exception e) {
            log.error("Error encountered while setting parameters of lend transaction");
        }
    }


    /**
     * Sets parameters of a lend transaction by populating the lend transaction object with related
     * borrow request and transaction details.
     *
     * @param lendTransaction The lend transaction object to set parameters for.
     */
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
