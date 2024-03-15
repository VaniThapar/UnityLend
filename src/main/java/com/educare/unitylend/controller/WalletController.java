package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
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
    private final WalletService walletService;

    //returns the list of all the wallets
     @GetMapping("/get-all-wallets")
    public ResponseEntity<List<Wallet>> getAllWallets() throws ControllerException, ServiceException {
        List<Wallet> wallets = walletService.getAllWallets();
        return ResponseEntity.ok().body(wallets);
    }

    @GetMapping("/wallet-for-userid/{userId}")
    public ResponseEntity<Wallet> getWalletForUserId(@PathVariable String userId) throws ControllerException, ServiceException {
        Wallet wallet = walletService.getWalletForUserId(userId);
        return ResponseEntity.ok().body(wallet);
    }

    @GetMapping("/wallet-for-walletid/{walletId}")
    public ResponseEntity<Wallet> getWalletForWalletId(@PathVariable String walletId) throws ControllerException, ServiceException {
        Wallet wallet = walletService.getWalletForId(walletId);
        return ResponseEntity.ok().body(wallet);
    }

    @GetMapping("/add-amount/{walletId}")
    public ResponseEntity<Boolean> addAmount(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException, ServiceException {
        boolean success = walletService.addAmount(walletId, amount);
        return ResponseEntity.ok().body(success);
    }

    @GetMapping("/deduct-amount/{walletId}")
    public ResponseEntity<Boolean> deductAmount(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException, ServiceException {
        boolean success = walletService.deductAmount(walletId, amount);
        return ResponseEntity.ok().body(success);
    }

    @GetMapping("/get-wallet-balance/{walletId}")
    public ResponseEntity<Float> getWalletBalance(@PathVariable String userId) throws ControllerException, ServiceException {
        Float amount = walletService.getWalletBalance(userId);
        return ResponseEntity.ok().body(amount);
    }

}