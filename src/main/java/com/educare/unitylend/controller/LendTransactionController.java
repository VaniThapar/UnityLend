package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.LendTransaction;
import com.educare.unitylend.service.LendTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/lend-transaction")
public class LendTransactionController extends BaseController {

    private LendTransactionService lendTransactionService;


    @PostMapping("/lend")
    public ResponseEntity<String> initiateLendTransaction(
            @RequestParam String lenderId,
            @RequestParam String borrowRequestId,
            @RequestParam BigDecimal amount
    ) throws ControllerException {
        try {
            if (lenderId.isEmpty() || borrowRequestId.isEmpty() || amount == null) {
                log.error("One of the required parameters is null");
                return ResponseEntity.badRequest().body(null);
            }
            Boolean isLendSuccessful = lendTransactionService.initiateLendTransaction(lenderId, borrowRequestId, amount);

            if(!isLendSuccessful){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lend transaction was unsuccessful");
            }

            return ResponseEntity.ok("Lend Transaction is successful");
        } catch (Exception e) {
            log.error("Error encountered in initiating a lend operation");
            throw new ControllerException("Error encountered in initiating a lend operation", e);
        }
    }

    @GetMapping("/get-lend-transaction-info/{transactionId}")
    public ResponseEntity<LendTransaction> getLendTransactionInfo(@PathVariable String transactionId) throws ControllerException {
        //Getting lending transaction info by its transactionId
        try {
            if (transactionId == null || transactionId.isEmpty()) {
                log.error("Transaction Id can not be null");
                return ResponseEntity.badRequest().body(null);
            }
            LendTransaction lendTransaction = lendTransactionService.getLendTransactionInfo(transactionId);
            if (lendTransaction == null) {
                log.error("No transaction found with given transaction id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(lendTransaction);
        } catch (Exception e) {
            log.error("Error encountered in getting the transaction info for the given id", e);
            throw new ControllerException("Error encountered in getting the transaction info for the given id", e);
        }
    }


    @GetMapping("/get-all-lend-transactions-by-user/{userId}")
    public ResponseEntity<List<LendTransaction>> getAllLendTransactionsByUserId(@PathVariable String userId) throws ControllerException {
        //Getting all the lending transactions made by user
        try {
            if (userId == null || userId.isEmpty()) {
                log.error("User id can not be null");
                return ResponseEntity.badRequest().body(null);
            }
            List<LendTransaction> lendTransactionsList = lendTransactionService.getLendTransactionsByUserId(userId);
            if (lendTransactionsList.isEmpty()) {
                log.error("Given user has not made any transactions");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lendTransactionsList);
        } catch (Exception e) {
            log.error("Error encountered in getting the lend transactions of user", e);
            throw new ControllerException("Error encountered in getting the lend transactions of user", e);
        }
    }


//    @GetMapping("/get-all-lend-transactions/{payerId}/{payeeId}")
//    public ResponseEntity<List<LendTransaction>> getAllLendTransactionsBetweenPayerAndPayee(@PathVariable String payerId, @PathVariable String payeeId) throws ControllerException {
//        //Getting all lending transaction between a payer and payee
//        try {
//            if (payerId == null || payerId.isEmpty()) {
//                log.error("Payer Id cannot be null");
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            if (payeeId == null || payeeId.isEmpty()) {
//                log.error("Payee Id cannot be null");
//                return ResponseEntity.badRequest().body(null);
//            }
//            List<LendTransaction> lendTransactionsList = lendTransactionService.getTransactionsBetweenPayerAndPayee(payerId, payeeId);
//            if (lendTransactionsList.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(lendTransactionsList);
//        } catch (Exception e) {
//            log.error("Error encountered in getting the transactions between payer and payee", e);
//            throw new ControllerException("Error encountered in getting the transactions between payer and payee", e);
//        }
//    }

    /*For Administration Purposes*/
    @GetMapping("/get-all-lend-transactions")
    ResponseEntity<List<LendTransaction>> getAllLendTransactions() throws ControllerException {
        try {
            List<LendTransaction> lendTransactions = lendTransactionService.getAllLendTransactions();
            if (lendTransactions.isEmpty()) {
                log.error("No lend transactions found");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lendTransactions);
        } catch (ServiceException e) {
            log.error("Error encountered in getting all lend transactions");
            throw new ControllerException("Error encountered in getting all lend transactions", e);
        }
    }
}
