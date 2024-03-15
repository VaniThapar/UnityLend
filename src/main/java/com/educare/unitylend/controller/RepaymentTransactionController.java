package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.RepaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/repayment-transaction")
public class RepaymentTransactionController extends BaseController{

    /**
     * @return List of all the available {@link RepaymentTransaction}
     * @throws ControllerException : Exception to be thrown from controller in case of any exception
     */

    @PostMapping
    public void createRepaymentTransaction() throws ControllerException {
    }

    @GetMapping
    public void getTransactionsForPayerId() throws ControllerException {
    }

    @GetMapping
    public void getTransactionForPayerIdAndPayeeId() throws ControllerException {
    }

    @GetMapping
    public void getTransactionsForTransactionId() throws ControllerException {
    }
}
