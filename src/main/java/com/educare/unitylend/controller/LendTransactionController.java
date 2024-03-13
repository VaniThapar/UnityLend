package com.educare.unitylend.controller;
import com.educare.unitylend.service.LendTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/lend-transaction")
public class LendTransactionController extends BaseController{
}
