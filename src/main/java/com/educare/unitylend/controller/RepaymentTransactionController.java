package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.LendTransaction;
import com.educare.unitylend.model.RepaymentTransaction;
import com.educare.unitylend.service.RepaymentTransactionService;
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
@RequestMapping("/repayment-transaction")
public class RepaymentTransactionController extends BaseController {
    RepaymentTransactionService repaymentTransactionService;

    /**
     * API endpoint for creating a repayment transaction.
     *
     * @param borrowRequestId The ID of the borrow request.
     * @return ResponseEntity<String> Indicates the success or failure of the transaction creation.
     * @throws ControllerException If an error occurs during the transaction creation process.
     */
    @PostMapping("/repay")
    public ResponseEntity<String> initiateRepaymentTransaction(
            @RequestParam String borrowRequestId
    ) throws ControllerException {
        try {
            if (borrowRequestId.isEmpty()) {
                log.error("Borrow Request Id is null");
                return ResponseEntity.badRequest().body(null);
            }
            Boolean isRepaymentSuccessful = repaymentTransactionService.initiateRepaymentTransaction(borrowRequestId);

            if (!isRepaymentSuccessful) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Repayment transaction was unsuccessful");
            }

            return ResponseEntity.ok("Repayment Transaction is successful");
        } catch (ServiceException e) {
            log.error("Error encountered in initiating a repayment operation");
            throw new ControllerException("Error encountered in initiating a repayment operation", e);
        }
    }


    /**
     * API Endpoint that initiates the repayment of a defaulted EMI.
     *
     * @param borrowRequestId The ID of the borrow request associated with the defaulted EMI.
     * @return ResponseEntity<String> A response entity indicating the success or failure of the repayment transaction.
     * @throws ControllerException If an error occurs during the repayment process.
     */
    @PostMapping("/repay-default-emi")
    public ResponseEntity<String> repayDefaultEMI(
            @RequestParam String borrowRequestId
    ) throws ControllerException {
        try {
            if (borrowRequestId.isEmpty()) {
                log.error("Borrow Request Id is null");
                return ResponseEntity.badRequest().body(null);
            }
            Boolean isRepaymentSuccessful = repaymentTransactionService.repayDefaultEMI(borrowRequestId);

            if (!isRepaymentSuccessful) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Repayment transaction for defaulted EMI was unsuccessful");
            }

            return ResponseEntity.ok("Repayment Transaction for defaulted EMI is successful");
        } catch (Exception e) {
            log.error("Error encountered in repaying the defaulted EMI");
            throw new ControllerException("Error encountered in in repaying the defaulted EMI", e);
        }
    }


    /**
     * API endpoint for retrieving repayment transactions for a specific payer ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity<List < RepaymentTransaction>> The list of repayment transactions associated with the specified user ID.
     * @throws ControllerException If an error occurs during the transaction retrieval process.
     */

    @GetMapping("/get-all-repayment-transactions-by-user/{userId}")
    public ResponseEntity<List<RepaymentTransaction>> getAllRepaymentTransactionsByUserId(@PathVariable String userId) throws ControllerException {
        try {
            if (userId == null || userId.isEmpty()) {
                log.info("User id is null");
                return ResponseEntity.badRequest().build();
            }

            List<RepaymentTransaction> repaymentTransactionList = repaymentTransactionService.getRepaymentTransactionsByUserId(userId);

            if (repaymentTransactionList.isEmpty()) {
                log.info("No repayment transactions found for the given user id");
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(repaymentTransactionList);
        } catch (Exception e) {
            log.error("Error in fetching repayment transaction details of the given user", e);
            throw new ControllerException("Error encountered in fetching repayment transaction details of the given user", e);
        }
    }

    /**
     * API Endpoint to retrieve information about a repayment transaction identified by its ID.
     *
     * @param repaymentTransactionId The ID of the repayment transaction to retrieve information for.
     * @return ResponseEntity<RepaymentTransaction> A response entity containing the information of the repayment transaction.
     * @throws ControllerException If an error occurs while fetching the repayment transaction information.
     */
    @GetMapping("/get-repayment-transaction-info/{repaymentTransactionId}")
    public ResponseEntity<RepaymentTransaction> getRepaymentTransactionInfo(@PathVariable String repaymentTransactionId) throws ControllerException {
        try {
            if (repaymentTransactionId == null || repaymentTransactionId.isEmpty()) {
                log.error("Repayment Transaction Id is null");
                return ResponseEntity.badRequest().body(null);
            }
            RepaymentTransaction repaymentTransaction = repaymentTransactionService.getRepaymentTransactionInfo(repaymentTransactionId);
            if (repaymentTransaction == null) {
                log.error("No repayment transaction found with given transaction id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(repaymentTransaction);
        } catch (Exception e) {
            log.error("Error encountered in getting the repayment transaction info for the given id", e);
            throw new ControllerException("Error encountered in getting the repayment transaction info for the given id", e);
        }
    }


    /**
     * API Endpoint to retrieve all repayment transactions.
     *
     * @return ResponseEntity<List < RepaymentTransaction>> A response entity containing a list of all repayment transactions.
     * @throws ControllerException If an error occurs while fetching all repayment transactions.
     */
    @GetMapping("/get-all-repayment-transactions")
    ResponseEntity<List<RepaymentTransaction>> getAllRepaymentTransactions() throws ControllerException {
        try {
            List<RepaymentTransaction> repaymentTransactionList = repaymentTransactionService.getAllRepaymentTransactions();
            if (repaymentTransactionList.isEmpty()) {
                log.error("No repayment transactions found");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(repaymentTransactionList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting all repayment transactions");
            throw new ControllerException("Error encountered in getting all repayment transactions", e);
        }
    }

}