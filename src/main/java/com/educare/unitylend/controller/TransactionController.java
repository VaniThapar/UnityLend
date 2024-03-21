package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Transaction;
import com.educare.unitylend.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController extends BaseController {

    private TransactionService transactionService;

    /**
     * API endpoint for retrieving transactions by sender ID.
     *
     * @param senderId The ID of the sender whose transactions are to be retrieved.
     * @return ResponseEntity<List < Transaction>> The list of transactions initiated by the specified sender.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction-by-sender/{senderId}")
    ResponseEntity<List<Transaction>> getTransactionsBySender(@PathVariable String senderId) throws ControllerException {
        try {
            if (senderId.isEmpty()) {
                log.error("Sender Id is null");
                return ResponseEntity.badRequest().body(null);
            }
            List<Transaction> transactionList = transactionService.getTransactionsBySender(senderId);
            if (transactionList.isEmpty()) {
                log.info("No transactions found for given sender");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactionList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching transactions by sender id");
            throw new ControllerException("Error encountered in fetching transactions by sender id", e);
        }
    }

    /**
     * API endpoint for retrieving transactions between a sender and a receiver.
     *
     * @param senderId   The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return ResponseEntity<List < Transaction>> The list of transactions between the specified sender and receiver.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction/{senderId}/{receiverId}")
    ResponseEntity<List<Transaction>> getTransactionsBySenderToReceiver(@PathVariable String senderId, @PathVariable String receiverId) throws ControllerException {
        try {
            if (senderId.isEmpty() || receiverId.isEmpty()) {
                log.error("Sender Id or Receiver Id is null");
                return ResponseEntity.badRequest().body(null);
            }
            List<Transaction> transactionList = transactionService.getTransactionsBySenderToReceiver(senderId, receiverId);
            if (transactionList.isEmpty()) {
                log.info("No transactions found for given sender and receiver");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactionList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting transactions between given sender and user");
            throw new ControllerException("Error encountered in getting transactions between given sender and user", e);
        }
    }


    /**
     * API endpoint for retrieving transactions of a sender within a specified date range.
     *
     * @param senderId  The ID of the sender.
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return ResponseEntity<List < Transaction>> The list of transactions initiated by the specified sender within the specified date range.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction-by-date/{senderId}")
    public ResponseEntity<List<Transaction>> getTransactionsOfSenderByDateRange(
            @PathVariable String senderId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ControllerException {
        try {
            if (senderId == null) {
                log.error("Sender ID is null");
                return ResponseEntity.badRequest().build();
            }

            if (startDate == null || endDate == null) {
                log.error("Start date or end date is null");
                return ResponseEntity.badRequest().build();
            }
            List<Transaction> transactionList = transactionService.getTransactionsOfSenderByDateRange(senderId, startDate, endDate);
            if (transactionList.isEmpty()) {
                log.info("No transactions found by sender in the given date range");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactionList);
        } catch (ServiceException e) {
            log.error("Error encountered while getting transactions by date");
            throw new ControllerException("Error encountered while getting transactions by date", e);
        }
    }


    /*For Administration Purposes*/

    /**
     * API endpoint for retrieving all transactions.
     *
     * @return ResponseEntity<List < Transaction>> The list of all transactions.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-all-transactions")
    ResponseEntity<List<Transaction>> getAllTransactions() throws ControllerException {
        try {
            List<Transaction> transactionList = transactionService.getAllTransactions();
            if (transactionList.isEmpty()) {
                log.info("No transactions found");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactionList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching all transactions");
            throw new ControllerException("Error encountered in fetching all transactions", e);
        }
    }
}
