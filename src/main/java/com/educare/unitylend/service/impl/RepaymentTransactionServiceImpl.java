package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.RepaymentTransactionRepository;
import com.educare.unitylend.dao.StatusRepository;
import com.educare.unitylend.dao.TransactionRepository;
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
public class RepaymentTransactionServiceImpl implements RepaymentTransactionService {
    private RepaymentTransactionRepository repaymentTransactionRepository;
    private TransactionRepository transactionRepository;
    private BorrowRequestService borrowRequestService;
    private TransactionService transactionService;
    private PaymentScheduleService paymentScheduleService;
    private EMIService emiService;
    private WalletService walletService;
    private StatusRepository statusRepository;


    /**
     * Initiates a repayment transaction for a borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to initiate the repayment transaction.
     * @return true if the repayment transaction is successfully initiated, false otherwise.
     * @throws ServiceException if an error occurs during the initiation process.
     */
    @Override
    public Boolean initiateRepaymentTransaction(String borrowRequestId) throws ServiceException {
        try {
            EMI nextScheduledEMI = paymentScheduleService.getNextScheduledEMI(borrowRequestId);

            if(nextScheduledEMI==null){
                throw new Exception("Next Scheduled EMI is null");
            }

            if (paymentScheduleService.isDefaulted(nextScheduledEMI)) {
                log.error("Your previous EMI payment is still overdue! Please pay it before proceeding for future EMI payments");
                return false;
            }

            Boolean isRepaymentSuccessful = true;
            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);

            if(borrowRequest==null){
                throw new Exception("Borrow request is null");
            }

            User borrower = borrowRequest.getBorrower();
            String borrowerId = borrower.getUserId();

            BigDecimal emiAmount = nextScheduledEMI.getEmiAmount();
            List<String> lenderIdList = transactionRepository.getLenderIdForRequestId(borrowRequestId);

            Boolean isDebitedFromBorrower = handleDebitFromBorrower(borrowerId, emiAmount);
            isRepaymentSuccessful = isRepaymentSuccessful && isDebitedFromBorrower;

            for (String lenderId : lenderIdList) {
                BigDecimal lenderEMIAmount = emiService.calculateLenderEMIAmount(borrowRequestId, lenderId);

                Boolean isCreditedToLender = handleCreditToLender(lenderId, lenderEMIAmount);
                Boolean isInsertionInTransactionSuccessful = transactionService.initiateTransaction(lenderId, borrowerId, lenderEMIAmount);

                isRepaymentSuccessful = isRepaymentSuccessful && isCreditedToLender;

                if (!isRepaymentSuccessful) {
                    return false;
                }
            }

            String transactionId = transactionService.getLatestTranscationId();
            Integer rowsAffectedInInsertionInRepaymentTransactionSuccessful = repaymentTransactionRepository.insertRepaymentTransaction(transactionId,
                    borrowRequestId);

            if(isRepaymentSuccessful && rowsAffectedInInsertionInRepaymentTransactionSuccessful > 0){
                Integer statusCode=statusRepository.getStatusCodeByStatusName("Completed");
                Status status=statusRepository.getStatusByStatusCode(statusCode);
                paymentScheduleService.updateEMIStatus(nextScheduledEMI,status);
            }

            return true;
        } catch (Exception e) {
            log.error("Error encountered while making repayment transaction", e);
            throw new ServiceException("Error encountered while making repayment transaction", e);
        }
    }


    /**
     * Repays the defaulted EMI for a borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to repay the defaulted EMI.
     * @return true if the defaulted EMI is successfully repaid, false otherwise.
     * @throws ServiceException if an error occurs during the repayment process.
     */
    public Boolean repayDefaultEMI(String borrowRequestId) throws ServiceException {
        try {
            Boolean isRepaymentSuccessful = true;
            EMI nextScheduledEMI = paymentScheduleService.getNextScheduledEMI(borrowRequestId);

            if(nextScheduledEMI==null){
                throw new Exception("Next Scheduled EMI is null");
            }

            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);

            if(borrowRequest==null){
                throw new Exception("Borrow request is null");
            }

            User borrower = borrowRequest.getBorrower();
            String borrowerId = borrower.getUserId();

            BigDecimal emiAmount = nextScheduledEMI.getEmiAmount();
            List<String> lenderIdList = transactionRepository.getLenderIdForRequestId(borrowRequestId);

            Boolean isDebitedFromBorrower = handleDebitFromBorrower(borrowerId, emiAmount);
            isRepaymentSuccessful = isRepaymentSuccessful && isDebitedFromBorrower;

            for (String lenderId : lenderIdList) {
                BigDecimal lenderEMIAmount = emiService.calculateLenderEMIAmount(borrowRequestId, lenderId);
                Boolean isCreditedToLender = handleCreditToLender(lenderId, lenderEMIAmount);
                Boolean isInsertionInTransactionSuccessful = transactionService.initiateTransaction(lenderId, borrowerId, lenderEMIAmount);
                isRepaymentSuccessful = isRepaymentSuccessful && isCreditedToLender;

                if (!isRepaymentSuccessful) {
                    return false;
                }
            }

            String transactionId = transactionService.getLatestTranscationId();
            Integer rowsAffectedInInsertionInRepaymentTransactionSuccessful = repaymentTransactionRepository.insertRepaymentTransaction(transactionId,
                    borrowRequestId);

            if(isRepaymentSuccessful && rowsAffectedInInsertionInRepaymentTransactionSuccessful > 0){
                Integer statusCode=statusRepository.getStatusCodeByStatusName("Completed");
                Status status=statusRepository.getStatusByStatusCode(statusCode);
                paymentScheduleService.updateEMIStatus(nextScheduledEMI,status);
            }

            return isRepaymentSuccessful;
        } catch (Exception e) {
            log.error("Error encountered while repaying the defaulted EMI", e);
            throw new ServiceException("Error encountered while repaying the defaulted EMI", e);
        }
    }


    /**
     * Handles the deduction of the EMI amount from the borrower's wallet.
     *
     * @param borrowerId The ID of the borrower from whose wallet the amount is to be deducted.
     * @param emiAmount The amount to be deducted from the borrower's wallet.
     * @return true if the deduction is successful, false otherwise.
     */
    private Boolean handleDebitFromBorrower(String borrowerId, BigDecimal emiAmount) {
        try {
            Wallet wallet = walletService.getWalletForUserId(borrowerId);
            String walletId = wallet.getWalletId();
            walletService.deductAmount(walletId, emiAmount);
            return true;
        } catch (ServiceException e) {
            log.error("Error encountered in debiting money from borrower's wallet");
            return false;
        }
    }


    /**
     * Handles the addition of the EMI amount to the lender's wallet.
     *
     * @param lenderId The ID of the lender to whose wallet the amount is to be added.
     * @param emiAmount The amount to be added to the lender's wallet.
     * @return true if the addition is successful, false otherwise.
     */
    private Boolean handleCreditToLender(String lenderId, BigDecimal emiAmount) {
        try {
            Wallet wallet = walletService.getWalletForUserId(lenderId);
            String walletId = wallet.getWalletId();
            walletService.addAmount(walletId, emiAmount);
            return true;
        } catch (ServiceException e) {
            log.error("Error encountered in crediting money to lender's wallet");
            return false;
        }
    }


    /**
     * Retrieves the repayment transactions associated with a specific user.
     *
     * @param userId The ID of the user for whom repayment transactions are to be retrieved.
     * @return A list of repayment transactions associated with the specified user.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<RepaymentTransaction> getRepaymentTransactionsByUserId(String userId) throws ServiceException {
        try {
            List<RepaymentTransaction> repaymentTransactions = repaymentTransactionRepository.getRepaymentTransactionsByUserId(userId);

            if(repaymentTransactions==null){
                throw new Exception("Repayment Transaction List is null");
            }

            setParametersOfRepaymentTransaction(repaymentTransactions);

            return repaymentTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching repayment transactions by user id");
            throw new ServiceException("Error encountered during fetching repayment transactions by user id", e);
        }
    }


    /**
     * Retrieves information about a specific repayment transaction.
     *
     * @param repaymentTransactionId The ID of the repayment transaction to retrieve information for.
     * @return The repayment transaction with the specified ID.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public RepaymentTransaction getRepaymentTransactionInfo(String repaymentTransactionId) throws ServiceException {
        try {
            RepaymentTransaction repaymentTransaction = repaymentTransactionRepository.getRepaymentTransactionById(repaymentTransactionId);

            if(repaymentTransaction==null){
                throw new Exception("Repayment Transaction is null");
            }

            setParametersOfRepaymentTransaction(repaymentTransaction);
            return repaymentTransaction;
        } catch (Exception e) {
            log.error("Error encountered while fetching repayment transaction with given id");
            throw new ServiceException("Error encountered while fetching repayment transaction with given id", e);
        }
    }


    /**
     * Sets parameters of a repayment transaction such as the associated borrow request and transaction.
     *
     * @param repaymentTransaction The repayment transaction for which parameters are to be set.
     */
    void setParametersOfRepaymentTransaction(RepaymentTransaction repaymentTransaction) {
        try {
            String repaymentTransactionId = repaymentTransaction.getRepaymentTransactionId();
            String borrowRequestId = repaymentTransactionRepository.getBorrowRequestId(repaymentTransactionId);
            String transactionId = repaymentTransactionRepository.getTransactionId(repaymentTransactionId);
            BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
            Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);

            repaymentTransaction.setBorrowRequest(borrowRequest);
            repaymentTransaction.setTransaction(transaction);
        } catch (Exception e) {
            log.error("Error encountered while setting parameters of repayment transaction");
        }
    }


    /**
     * Sets parameters of repayment transactions such as the associated borrow requests and transactions.
     *
     * @param repaymentTransactionList The list of repayment transactions for which parameters are to be set.
     */
    void setParametersOfRepaymentTransaction(List<RepaymentTransaction> repaymentTransactionList) {
        try {
            for (RepaymentTransaction repaymentTransaction : repaymentTransactionList) {
                String repaymentTransactionId = repaymentTransaction.getRepaymentTransactionId();
                String borrowRequestId = repaymentTransactionRepository.getBorrowRequestId(repaymentTransactionId);
                String transactionId = repaymentTransactionRepository.getTransactionId(repaymentTransactionId);
                BorrowRequest borrowRequest = borrowRequestService.getBorrowRequestByRequestId(borrowRequestId);
                Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);

                repaymentTransaction.setBorrowRequest(borrowRequest);
                repaymentTransaction.setTransaction(transaction);
            }
        } catch (ServiceException e) {
            log.error("Error encountered while setting parameters of repayment transaction");
        }
    }


    /**
     * Retrieves all repayment transactions.
     *
     * @return A list containing all repayment transactions.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<RepaymentTransaction> getAllRepaymentTransactions() throws ServiceException {
        try {
            List<RepaymentTransaction> repaymentTransactions = repaymentTransactionRepository.getAllRepaymentTransactions();

            if(repaymentTransactions==null){
                throw new Exception("Repayment Transaction List is null");
            }

            setParametersOfRepaymentTransaction(repaymentTransactions);
            return repaymentTransactions;
        } catch (Exception e) {
            log.error("Error encountered during fetching all repayment transactions");
            throw new ServiceException("Error encountered during fetching all repayment transactions", e);
        }
    }

}