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
    @Override
    public Boolean initiateTransaction(String senderId, String receiverId, BigDecimal amount) throws ServiceException {
        try{
            int rowsAffected=transactionRepository.insertTransaction(senderId,receiverId,amount);
            return rowsAffected>0;
        }
        catch (Exception e){
        log.error("Error encountered in inserting record in Transaction table");
        throw new ServiceException("Error encountered in inserting record in Transaction table",e);
        }
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

    @Override
    public Transaction getTransactionByTransactionId(String transactionId) throws ServiceException{
        try{
            Transaction transaction=transactionRepository.getTransactionByTransactionId(transactionId);
            String senderId=transactionRepository.getSenderIdByTransactionId(transactionId);
            String receiverId=transactionRepository.getReceiverIdByTransactionId(transactionId);
            Integer statusCode=transactionRepository.getStatusCodeByTransactionId(transactionId);

            User sender=userRepository.getUserForUserId(senderId);
            User receiver=userRepository.getUserForUserId(receiverId);
            Status transactionStatus=statusRepository.getStatusByStatusCode(statusCode);

            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setTransactionStatus(transactionStatus);

            return transaction;
        }
        catch (Exception e){
            log.error("Error encountered in fetching transaction by transaction id");
            throw new ServiceException("Error encountered in fetching transaction by transaction id",e);
        }
    }

    @Override
    public String getLatestTranscationId() throws ServiceException {
        try{
            String transactionId=transactionRepository.getLatestTransactionId();
            return transactionId;
        }
        catch(Exception e){
            log.error("Error encountered in getting latest transaction id");
            throw new ServiceException("Error encountered in getting latest transaction id",e);
        }
    }

}
