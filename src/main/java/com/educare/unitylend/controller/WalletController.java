package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.UserService;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {

    WalletService walletService;

    @GetMapping("/get-user-info/{userId}")

    public Wallet getWalletInfo(@PathVariable String userId) throws ControllerException{
        //Getting wallet information
        try {
            return walletService.getWalletInfo(userId);
        } catch (Exception e) {
            log.error("Error encountered in getting the wallet info for the given user");
            throw new ControllerException("Error encountered in getting the wallet info for the given user", e);
        }
    }

    @PostMapping("/generate-wallet")

    public ResponseEntity<String> generateWallet(@RequestBody Wallet wallet) throws ControllerException {
        // Generating the wallet
        try {
            walletService.generateWallet(wallet);
            return ResponseEntity.ok("succcessfully created wallet!!!");
        } catch (Exception e) {
            log.error("Error encountered in generating the wallet");
            throw new ControllerException("Error encountered in generating the wallet", e);
        }

    }





}


