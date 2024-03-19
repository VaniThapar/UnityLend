package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {
    private final WalletService walletService;

    /**
     * API endpoint for retrieving a wallet for a specific user ID.
     *
     * @param userId The ID of the user for whom the wallet is to be retrieved.
     * @return ResponseEntity<Wallet> The wallet associated with the specified user ID.
     * @throws ControllerException If an error occurs during the wallet retrieval process.
     */
    @GetMapping("/get-wallet-for-user-id/{userId}")
    public ResponseEntity<Wallet> getWalletForUserId(@PathVariable String userId) throws ControllerException, ServiceException {
        try {
            if(userId==null||userId.isEmpty()){
                log.error("User id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }

            Wallet wallet = walletService.getWalletForUserId(userId);
            if (wallet == null) {
                log.info("No wallet found for given user id");
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(wallet);
        } catch (ServiceException e) {
            log.error("Error encountered in getting wallet by user id");
            throw new ControllerException("Error encountered in getting wallet by user id", e);
        }
    }


    /**
     * API endpoint for retrieving a wallet for a specific wallet ID.
     *
     * @param walletId The ID of the wallet to be retrieved.
     * @return ResponseEntity<Wallet> The wallet associated with the specified wallet ID.
     * @throws ControllerException If an error occurs during the wallet retrieval process.
     */
    @GetMapping("/get-wallet-for-wallet-id/{walletId}")
    public ResponseEntity<Wallet> getWalletForWalletId(@PathVariable String walletId) throws ControllerException, ServiceException {
        try {
            if(walletId==null||walletId.isEmpty()){
                log.error("Wallet id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }

            Wallet wallet = walletService.getWalletForWalletId(walletId);
            if (wallet == null) {
                log.info("No wallet found for given wallet id");
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(wallet);
        } catch (ServiceException e) {
            log.error("Error encountered in getting wallet by wallet id");
            throw new ControllerException("Error encountered in getting wallet by wallet id", e);
        }
    }


    /**
     * API endpoint for adding an amount to a wallet.
     *
     * @param walletId The ID of the wallet to which the amount will be added.
     * @param amount   The amount to be added.
     * @return ResponseEntity<Boolean> Indicates the success or failure of the operation.
     * @throws ControllerException If an error occurs during the process.
     */
    @GetMapping("/add-amount/{walletId}")
    public ResponseEntity<String> addAmount(@PathVariable String walletId, @RequestParam BigDecimal amount) throws ControllerException, ServiceException {
        try{
            if(walletId==null||walletId.isEmpty()){
                log.error("Wallet id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }

            Boolean isAdded = walletService.addAmount(walletId, amount);
            if(!isAdded){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Amount could not be added to the wallet");
            }
            return ResponseEntity.ok().body("Amount successfully added to the wallet");
        }
        catch(ServiceException e){
            log.error("Error encountered in adding amount to wallet");
            throw new ControllerException("Error encountered in adding amount to wallet",e);
        }
    }

    /**
     * API endpoint for deducting an amount from a wallet.
     *
     * @param walletId The ID of the wallet from which the amount will be deducted.
     * @param amount   The amount to be deducted.
     * @return ResponseEntity<Boolean> Indicates the success or failure of the operation.
     * @throws ControllerException If an error occurs during the process.
     */
    @GetMapping("/deduct-amount/{walletId}")
    public ResponseEntity<String> deductAmount(@PathVariable String walletId, @RequestParam BigDecimal amount) throws ControllerException, ServiceException {
        try{
            if(walletId==null||walletId.isEmpty()){
                log.error("Wallet id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }

            Wallet wallet=walletService.getWalletForWalletId(walletId);
            BigDecimal balance=wallet.getBalance();

            if(balance.compareTo(amount)<0){
                return ResponseEntity.badRequest().body("Insufficient balance! Cannot deduct given amount");
            }

            Boolean isDeducted = walletService.deductAmount(walletId,amount);
            if(!isDeducted){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Amount could not be deducted from the wallet");
            }
            return ResponseEntity.ok("Amount deducted from the wallet successfully");
        }
        catch(ServiceException e){
            log.error("Error encountered in deducting amount from wallet");
            throw new ControllerException("Error encountered in deducting amount from wallet",e);
        }
    }


    /**
     * API endpoint for retrieving the balance of a wallet.
     *
     * @param walletId The ID of the wallet for which the balance is to be retrieved.
     * @return ResponseEntity<Float> The balance of the specified wallet.
     * @throws ControllerException If an error occurs during the balance retrieval process.
     */
    @GetMapping("/get-wallet-balance/{walletId}")
    public ResponseEntity<String> getWalletBalance(@PathVariable String walletId) throws ControllerException, ServiceException {
        try{
            if(walletId==null||walletId.isEmpty()){
                log.error("Wallet id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }

            BigDecimal balance = walletService.getWalletBalance(walletId);
            return ResponseEntity.ok().body("Wallet Balance is : "+balance);
        }
        catch(ServiceException e){
            log.error("Error encountered while getting wallet balance");
            throw new ControllerException("Error encountered while getting wallet balance",e);
        }
    }

    /*For Administration Purposes*/

    /**
     * API endpoint for retrieving all wallets.
     *
     * @return ResponseEntity<List < Wallet>> The list of all wallets.
     * @throws ControllerException If an error occurs during the wallet retrieval process.
     */
    @GetMapping("/get-all-wallets")
    public ResponseEntity<List<Wallet>> getAllWallets() throws ControllerException, ServiceException {
        try {
            List<Wallet> walletList = walletService.getAllWallets();
            if (walletList.isEmpty()) {
                log.info("No wallets found");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(walletList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching all wallets");
            throw new ServiceException("Error encountered in fetching all wallets", e);
        }
    }

}