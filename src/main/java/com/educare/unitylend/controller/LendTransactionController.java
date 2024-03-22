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

    /**
     * Initiates a lending transaction.
     *
     * @param lenderId        The ID of the lender initiating the transaction.
     * @param borrowRequestId The ID of the borrowing request for which the transaction is initiated.
     * @param amount          The amount to be lent in the transaction.
     * @return ResponseEntity<String> A response entity indicating the status of the transaction.
     * @throws ControllerException If an error occurs during the lending transaction initiation process.
     */
    @PostMapping("/lend")
    public ResponseEntity<String> initiateLendTransaction(
            @RequestParam(required=true) String lenderId ,
            @RequestParam(required=true) String borrowRequestId,
            @RequestParam(required=true) BigDecimal amount
    ) throws ControllerException {
        try {

            Boolean isLendSuccessful = lendTransactionService.initiateLendTransaction(lenderId, borrowRequestId, amount);

            if (!isLendSuccessful) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lend transaction was unsuccessful");
            }

            return ResponseEntity.ok("Lend Transaction is successful");
        } catch (Exception e) {
            log.error("Error encountered in initiating a lend operation");
            throw new ControllerException("Error encountered in initiating a lend operation", e);
        }
    }


    /**
     * Retrieves information about a lend transaction based on the provided ID.
     *
     * @param lendTransactionId The ID of the lend transaction to retrieve information for.
     * @return ResponseEntity<LendTransaction> A response entity containing the lend transaction information.
     * @throws ControllerException If an error occurs while retrieving the lend transaction information.
     */
    @GetMapping("/get-lend-transaction-info/{lendTransactionId}")
    public ResponseEntity<LendTransaction> getLendTransactionInfo(@PathVariable(required = true) String lendTransactionId) throws ControllerException {
        try {
            LendTransaction lendTransaction = lendTransactionService.getLendTransactionInfo(lendTransactionId);
            if (lendTransaction == null) {
                log.error("No lend transaction found with given transaction id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(lendTransaction);
        } catch (Exception e) {
            log.error("Error encountered in getting the lend transaction info for the given id", e);
            throw new ControllerException("Error encountered in getting the lend transaction info for the given id", e);
        }
    }


    /**
     * Retrieves all lend transactions associated with a user based on the provided user ID.
     *
     * @param userId The ID of the user to retrieve lend transactions for.
     * @return ResponseEntity<List < LendTransaction>> A response entity containing a list of lend transactions.
     * @throws ControllerException If an error occurs while retrieving the lend transactions.
     */
    @GetMapping("/get-all-lend-transactions-by-user/{userId}")
    public ResponseEntity<List<LendTransaction>> getAllLendTransactionsByUserId(@PathVariable(required = true) String userId) throws ControllerException {
        try {
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


    /**
     * Retrieves all lend transactions.
     *
     * @return ResponseEntity<List < LendTransaction>> A response entity containing a list of all lend transactions.
     * @throws ControllerException If an error occurs while retrieving the lend transactions.
     */

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
