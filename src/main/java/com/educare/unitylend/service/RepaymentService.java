//package com.educare.unitylend.service;
//
//import com.educare.unitylend.Exception.ServiceException;
//import com.educare.unitylend.model.RepaymentTransaction;
//
//public interface RepaymentService {
//    boolean createRepaymentTransaction(String PayerId, String PayeeId, String requestId, Float amount) throws ServiceException;
//
//    RepaymentTransaction getTransactionsForPayerId(String payeeId) throws ServiceException;
//
//    RepaymentTransaction getTransactionForPayerIdAndPayeeId(String payerId, String payeeId) throws ServiceException;
//
//    RepaymentTransaction getTransactionsForTransactionId(String transactionId) throws ServiceException;
//}
