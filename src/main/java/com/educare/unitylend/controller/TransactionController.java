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
     * API endpoint for initiating a transaction.
     *
     * @param transaction The transaction object containing transaction details to be initiated.
     * @return ResponseEntity<Boolean> Indicating success or failure of the transaction initiation process.
     * @throws ControllerException If an error occurs during the transaction initiation process.
     */
    @PostMapping("/initiate-transaction")
    ResponseEntity<Boolean> initiateTransaction(@RequestBody Transaction transaction) throws ControllerException {
        return null;
    }


    /**
     * API endpoint for retrieving transactions by sender ID.
     *
     * @param userId The ID of the sender whose transactions are to be retrieved.
     * @return ResponseEntity<List<Transaction>> The list of transactions initiated by the specified sender.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction-by-sender")
    ResponseEntity<List<Transaction>> getTransactionsBySender(@PathVariable String userId) throws ControllerException {
        return null;
    }

    /**
     * API endpoint for retrieving transactions between a sender and a receiver.
     *
     * @param senderId   The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return ResponseEntity<List<Transaction>> The list of transactions between the specified sender and receiver.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction/{senderId}/{receiverId}")
    ResponseEntity<List<Transaction>> getTransactionsBySenderToReceiver(@PathVariable String senderId, @PathVariable String receiverId) throws ControllerException {
        return null;
    }


    /**
     * API endpoint for retrieving transactions of a sender within a specified date range.
     *
     * @param senderId  The ID of the sender.
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return ResponseEntity<List<Transaction>> The list of transactions initiated by the specified sender within the specified date range.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    @GetMapping("/get-transaction-by-date/{senderId}")
    public ResponseEntity<List<Transaction>> getTransactionsOfSenderByDateRange(
            @PathVariable String senderId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ControllerException {
        try{
            List<Transaction> transactions = transactionService.getTransactionsOfSenderByDateRange(senderId, startDate, endDate);
            return ResponseEntity.ok(transactions);
        }
        catch(ServiceException e){
            log.error("Error while getting transactions by date");
            throw new ControllerException("Error while getting transactions by date",e);
        }
    }

    /*For Administration Purposes*/
    /**
     * API endpoint for retrieving all transactions.
     *
     * @return ResponseEntity<List<Transaction>> The list of all transactions.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */
    ResponseEntity<List<Transaction>> getAllTransactions() throws ControllerException {
        return null;
    }
}
