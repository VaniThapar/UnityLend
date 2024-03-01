package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {

    WalletService walletService;
    @GetMapping("/get-user-info/{userId}")

    public Wallet getWalletInfo(@PathVariable String userId) throws ControllerException{

        try {
            return walletService.getWalletInfo(userId);
        } catch (Exception e) {
            log.error("Error encountered in getting the wallet info for the given user");
            throw new ControllerException("Error encountered in getting the wallet info for the given user", e);
        }
    }

    }







