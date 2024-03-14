package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

public class TransactionController extends BaseController{

    ResponseEntity<List<Transaction>> getTransactionsBySender(@PathVariable String userId) throws ControllerException{
        return null;
    };

    ResponseEntity<List<Transaction>> getTransactionsBetween(@PathVariable String senderId, @PathVariable String receiverId) throws ControllerException
    {
        return null;
    };
    ResponseEntity<List<Transaction>> getAllTransactions() throws ControllerException
    {
        return null;
    };

    ResponseEntity<List<Transaction>> getTransactionsByDate(@PathVariable LocalDate date) throws ControllerException
    {
        return null;
    };
    ResponseEntity<Transaction> initiateTransaction( @RequestBody Transaction transaction) throws ControllerException
    {
        return null;
    };
}
