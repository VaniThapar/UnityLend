//package com.educare.unitylend.controller;
//
//import com.educare.unitylend.Exception.ControllerException;
//import com.educare.unitylend.model.RepaymentTransaction;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@AllArgsConstructor
//@RestController
//@RequestMapping("/repayment-transaction")
//public class RepaymentTransactionController extends BaseController{
//
//    /**
//     * @return List of all the available {@link RepaymentTransaction}
//     * @throws ControllerException : Exception to be thrown from controller in case of any exception
//     */
//
//
//    /**
//     * API endpoint for creating a repayment transaction.
//     *
//     * @param payerId   The ID of the payer.
//     * @param payeeId   The ID of the payee.
//     * @param requestId The ID of the request.
//     * @param amount    The amount to be repaid.
//     * @return ResponseEntity<Boolean> Indicates the success or failure of the transaction creation.
//     * @throws ControllerException If an error occurs during the transaction creation process.
//     */
//    @GetMapping("/make-repayment/{payerId}/{payeeId}/{requestId}/{amount}")
//    public ResponseEntity<Boolean> createRepaymentTransaction(@PathVariable String payerId, @PathVariable String payeeId, @PathVariable String requestId, @PathVariable Float amount) throws ControllerException {
//        return null;
//    }
//
//    /**
//     * API endpoint for retrieving repayment transactions for a specific payer ID.
//     *
//     * @param payerId The ID of the payer.
//     * @return ResponseEntity<RepaymentTransaction> The repayment transaction associated with the specified payer ID.
//     * @throws ControllerException If an error occurs during the transaction retrieval process.
//     */
//    @GetMapping("/repayment-details-for-payerid/{payerId}")
//    public ResponseEntity<RepaymentTransaction> getTransactionsForPayerId(@PathVariable String payerId) throws ControllerException {
//        return null;
//    }
//
//    /**
//     * API endpoint for retrieving repayment transactions between a payer and a payee.
//     *
//     * @param payerId The ID of the payer.
//     * @param payeeId The ID of the payee.
//     * @return ResponseEntity<RepaymentTransaction> The repayment transaction between the specified payer and payee.
//     * @throws ControllerException If an error occurs during the transaction retrieval process.
//     */
//    @GetMapping("/repayment-between-payerid-and-payeeid/{payerId}/{payeeId}")
//    public ResponseEntity<RepaymentTransaction> getTransactionForPayerIdAndPayeeId(@PathVariable String payerId, @PathVariable String payeeId) throws ControllerException {
//        return null;
//    }
//
//    /**
//     * API endpoint for retrieving repayment transactions for a specific transaction ID.
//     *
//     * @param transactionId The ID of the transaction.
//     * @return ResponseEntity<RepaymentTransaction> The repayment transaction associated with the specified transaction ID.
//     * @throws ControllerException If an error occurs during the transaction retrieval process.
//     */
//    @GetMapping("repayment-for-transactionid/{transactionId}")
//    public ResponseEntity<RepaymentTransaction> getTransactionsForTransactionId(@PathVariable String transactionId) throws ControllerException {
//        return null;
//    }
//
//}
