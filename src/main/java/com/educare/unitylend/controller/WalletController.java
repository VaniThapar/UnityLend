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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {

    WalletService walletService;

    /**
     * @return List of all the available {@link Wallet}
     * @throws ControllerException : Exception to be thrown from controller in case of any exception
     */

    @GetMapping("all-wallets")
    public List<Wallet> getAllWallets() throws ControllerException {
        try {
            List<Wallet> walletList = walletService.getWallets();
            return walletList;
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }
    }

    @GetMapping("/get-user-info/{userId}")
    public Wallet getWalletInfo(@PathVariable String userId) throws ControllerException{

        try {
            return walletService.getWalletInfo(userId);
        } catch (Exception e) {
            log.error("Error encountered in getting the wallet info for the given user");
            throw new ControllerException("Error encountered in getting the wallet info for the given user", e);
        }
    }

    @GetMapping("get-wallet/{walletId}")
    public Wallet getWalletById(@PathVariable String walletId) throws ControllerException {
        try {
            return walletService.getWalletById(walletId);
        } catch (Exception e) {
            log.error("Error in fetching desired wallet's details");
            throw new ControllerException("Error encountered in getting the wallet info for the given walletId", e);
        }
    }

    @PostMapping("/{walletId}/addAmount")
    public ResponseEntity<String> addAmountToWallet(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException{
        try {
            walletService.addAmountToWallet(walletId, amount);
            return ResponseEntity.ok("Amount added successfully to wallet");
        } catch (Exception e) {
            log.error("Error adding requested amount to wallet");
            throw new ControllerException("Error encountered while adding requested amount to wallet", e);
        }
    }

    @PostMapping("/{walletId}/debitAmount")
    public ResponseEntity<String> debitFromWallet(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException{
        try {
            walletService.debitFromWallet(walletId, amount);
            return ResponseEntity.ok("Amount debited successfully from wallet");
        } catch (Exception e) {
            log.error("Error debiting requested amount from wallet");
            throw new ControllerException("Error encountered while debiting requested amount from wallet", e);
        }
    }
}
