package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.RepaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/repayment-transaction")
public class RepaymentTransactionController extends BaseController{

    /**
     * @return List of all the available {@link RepaymentTransaction}
     * @throws ControllerException : Exception to be thrown from controller in case of any exception
     */


    @GetMapping("/make-repayment/{payerId}/{payeeId}/{amount}")
    public ResponseEntity<Boolean> createRepaymentTransaction(@PathVariable String payerId, @PathVariable String payeeId, @PathVariable String requestId, @PathVariable Float amount) throws ControllerException {
        return null;
    }

    @GetMapping("/repayment-details-for-payerid/{payerId}")
    public ResponseEntity<RepaymentTransaction> getTransactionsForPayerId(@PathVariable String payerId) throws ControllerException {
        return null;
    }

    @GetMapping("/repayment-between-payerid-and-payeeid/{payerId}/{payeeId}")
    public ResponseEntity<RepaymentTransaction> getTransactionForPayerIdAndPayeeId() throws ControllerException {
        return null;
    }

    @GetMapping("repayment-for-transactionid/{transactionId}")
    public ResponseEntity<RepaymentTransaction> getTransactionsForTransactionId(@PathVariable String transactionId) throws ControllerException {
        return null;
    }
}
