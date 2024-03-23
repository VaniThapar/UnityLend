package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.StatusRepository;
import com.educare.unitylend.dao.TransactionRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;


    /**
     * Initiates a transaction between two users.
     *
     * @param senderId   The ID of the user initiating the transaction.
     * @param receiverId The ID of the user receiving the transaction.
     * @param amount     The amount to be transacted.
     * @return True if the transaction is successfully initiated, false otherwise.
     * @throws ServiceException If an error occurs during the transaction initiation process.
     */
    @Override
    public Boolean initiateTransaction(String senderId, String receiverId, BigDecimal amount) throws ServiceException {
        try {
            int rowsAffected = transactionRepository.insertTransaction(senderId, receiverId, amount);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error encountered in inserting record in Transaction table");
            throw new ServiceException("Error encountered in inserting record in Transaction table", e);
        }
    }


    /**
     * Retrieves all transactions initiated by a specific user.
     *
     * @param senderId The ID of the user whose transactions are to be retrieved.
     * @return A list of transactions initiated by the specified user.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<Transaction> getDebitTransactionsForUser(String senderId) throws ServiceException {
        try {
            List<Transaction> transactionList = transactionRepository.getDebitTransactionsForUser(senderId);
            for (Transaction transaction : transactionList) {
                String receiverId = transactionRepository.getReceiverIdByTransactionId(transaction.getTransactionId());
                Integer statusCode = transactionRepository.getStatusCodeByTransactionId(transaction.getTransactionId());

                User sender = userRepository.getUserForUserId(senderId);
                User receiver = userRepository.getUserForUserId(receiverId);
                Status transactionStatus = statusRepository.getStatusByStatusCode(statusCode);

                transaction.setSender(sender);
                transaction.setReceiver(receiver);
                transaction.setTransactionStatus(transactionStatus);
            }

            return transactionList;
        } catch (Exception e) {
            log.error("Error encountered in fetching all transactions by sender");
            throw new ServiceException("Error encountered in fetching all transactions by sender", e);
        }
    }


    /**
     * Retrieves all transactions initiated by a specific sender and received by a specific receiver.
     *
     * @param senderId   The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return A list of transactions initiated by the sender and received by the receiver.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<Transaction> getTransactions(String senderId, String receiverId) throws ServiceException {
        try {
            List<Transaction> transactionList = transactionRepository.getTransactions(senderId, receiverId);
            for (Transaction transaction : transactionList) {
                Integer statusCode = transactionRepository.getStatusCodeByTransactionId(transaction.getTransactionId());

                User sender = userRepository.getUserForUserId(senderId);
                User receiver = userRepository.getUserForUserId(receiverId);
                Status transactionStatus = statusRepository.getStatusByStatusCode(statusCode);

                transaction.setSender(sender);
                transaction.setReceiver(receiver);
                transaction.setTransactionStatus(transactionStatus);
            }

            return transactionList;
        } catch (Exception e) {
            log.error("Error encountered in getting transactions between given sender and receiver");
            throw new ServiceException("Error encountered in getting transactions between given sender and receiver", e);
        }
    }


    /**
     * Retrieves all transactions initiated by a specific sender within a specified date range.
     *
     * @param senderId   The ID of the sender.
     * @param startDate  The start date of the range.
     * @param endDate    The end date of the range.
     * @return A list of transactions initiated by the sender within the specified date range.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<Transaction> getDebitTransactionByDateRangeForUser(String senderId, LocalDate startDate, LocalDate endDate) throws ServiceException {
        try {
            List<Transaction> transactionList = transactionRepository.getDebitTransactionByDateRangeForUser(senderId, startDate, endDate);
            for (Transaction transaction : transactionList) {
                String receiverId = transactionRepository.getReceiverIdByTransactionId(transaction.getTransactionId());
                Integer statusCode = transactionRepository.getStatusCodeByTransactionId(transaction.getTransactionId());

                User sender = userRepository.getUserForUserId(senderId);
                User receiver = userRepository.getUserForUserId(receiverId);
                Status transactionStatus = statusRepository.getStatusByStatusCode(statusCode);

                transaction.setSender(sender);
                transaction.setReceiver(receiver);
                transaction.setTransactionStatus(transactionStatus);
            }

            return transactionList;
        } catch (Exception e) {
            log.error("Error encountered in getting transactions by sender in the given date range");
            throw new ServiceException("Error encountered in getting transactions by sender in the given date range", e);
        }
    }


    /**
     * Retrieves all transactions along with their associated sender, receiver, and status information.
     *
     * @return A list of all transactions with sender, receiver, and status details.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<Transaction> getAllTransactions() throws ServiceException {
        try {
            List<Transaction> transactionList = transactionRepository.getAllTransactions();

            for (Transaction transaction : transactionList) {
                String receiverId = transactionRepository.getReceiverIdByTransactionId(transaction.getTransactionId());
                String senderId = transactionRepository.getSenderIdByTransactionId(transaction.getTransactionId());
                Integer statusCode = transactionRepository.getStatusCodeByTransactionId(transaction.getTransactionId());

                User sender = userRepository.getUserForUserId(senderId);
                User receiver = userRepository.getUserForUserId(receiverId);
                Status transactionStatus = statusRepository.getStatusByStatusCode(statusCode);

                transaction.setSender(sender);
                transaction.setReceiver(receiver);
                transaction.setTransactionStatus(transactionStatus);
            }

            return transactionList;
        } catch (Exception e) {
            log.error("Error encountered in getting all transactions");
            throw new ServiceException("Error encountered in getting all transactions", e);
        }
    }


    /**
     * Retrieves a transaction by its unique transaction ID along with associated sender, receiver, and status information.
     *
     * @param transactionId The unique identifier of the transaction to retrieve.
     * @return The transaction object with sender, receiver, and status details.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public Transaction getTransactionByTransactionId(String transactionId) throws ServiceException {
        try {
            Transaction transaction = transactionRepository.getTransactionByTransactionId(transactionId);

            if(transaction==null){
                throw new Exception("Transaction is null");
            }

            String senderId = transactionRepository.getSenderIdByTransactionId(transactionId);
            String receiverId = transactionRepository.getReceiverIdByTransactionId(transactionId);
            Integer statusCode = transactionRepository.getStatusCodeByTransactionId(transactionId);

            User sender = userRepository.getUserForUserId(senderId);
            User receiver = userRepository.getUserForUserId(receiverId);
            Status transactionStatus = statusRepository.getStatusByStatusCode(statusCode);

            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setTransactionStatus(transactionStatus);

            return transaction;
        } catch (Exception e) {
            log.error("Error encountered in fetching transaction by transaction id");
            throw new ServiceException("Error encountered in fetching transaction by transaction id", e);
        }
    }


    /**
     * Retrieves the ID of the latest transaction.
     *
     * @return The ID of the latest transaction.
     * @throws ServiceException If an error occurs while fetching the latest transaction ID.
     */
    @Override
    public String getLatestTranscationId() throws ServiceException {
        try {
            String transactionId = transactionRepository.getLatestTransactionId();
            return transactionId;
        } catch (Exception e) {
            log.error("Error encountered in getting latest transaction id");
            throw new ServiceException("Error encountered in getting latest transaction id", e);
        }
    }

}
