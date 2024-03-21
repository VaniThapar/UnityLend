//package com.educare.unitylend.controller;
//
//import com.educare.unitylend.Exception.ControllerException;
//import com.educare.unitylend.Exception.ServiceException;
//import com.educare.unitylend.model.Wallet;
//import com.educare.unitylend.service.WalletService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@AllArgsConstructor
//@RestController
//@RequestMapping("/wallet")
//public class WalletController extends BaseController {
//    private final WalletService walletService;
//
//    /**
//     * API endpoint for retrieving all wallets.
//     *
//     * @return ResponseEntity<List<Wallet>> The list of all wallets.
//     * @throws ControllerException If an error occurs during the wallet retrieval process.
//     */
//    public ResponseEntity<List<Wallet>> getAllWallets() throws ControllerException, ServiceException {
//        List<Wallet> wallets = walletService.getAllWallets();
//        return ResponseEntity.ok().body(wallets);
//    }
//
//    /**
//     * API endpoint for retrieving a wallet for a specific user ID.
//     *
//     * @param userId The ID of the user for whom the wallet is to be retrieved.
//     * @return ResponseEntity<Wallet> The wallet associated with the specified user ID.
//     * @throws ControllerException If an error occurs during the wallet retrieval process.
//     */
//    @GetMapping("/wallet-for-userid/{userId}")
//    public ResponseEntity<Wallet> getWalletForUserId(@PathVariable String userId) throws ControllerException, ServiceException {
//        Wallet wallet = walletService.getWalletForUserId(userId);
//        return ResponseEntity.ok().body(wallet);
//    }
//
//    /**
//     * API endpoint for retrieving a wallet for a specific wallet ID.
//     *
//     * @param walletId The ID of the wallet to be retrieved.
//     * @return ResponseEntity<Wallet> The wallet associated with the specified wallet ID.
//     * @throws ControllerException If an error occurs during the wallet retrieval process.
//     */
//    @GetMapping("/wallet-for-walletid/{walletId}")
//    public ResponseEntity<Wallet> getWalletForWalletId(@PathVariable String walletId) throws ControllerException, ServiceException {
//        Wallet wallet = walletService.getWalletForId(walletId);
//        return ResponseEntity.ok().body(wallet);
//    }
//
//    /**
//     * API endpoint for adding an amount to a wallet.
//     *
//     * @param walletId The ID of the wallet to which the amount will be added.
//     * @param amount   The amount to be added.
//     * @return ResponseEntity<Boolean> Indicates the success or failure of the operation.
//     * @throws ControllerException If an error occurs during the process.
//     */
//    @GetMapping("/add-amount/{walletId}")
//    public ResponseEntity<Boolean> addAmount(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException, ServiceException {
//        boolean success = walletService.addAmount(walletId, amount);
//        return ResponseEntity.ok().body(success);
//    }
//
//    /**
//     * API endpoint for deducting an amount from a wallet.
//     *
//     * @param walletId The ID of the wallet from which the amount will be deducted.
//     * @param amount   The amount to be deducted.
//     * @return ResponseEntity<Boolean> Indicates the success or failure of the operation.
//     * @throws ControllerException If an error occurs during the process.
//     */
//    @GetMapping("/deduct-amount/{walletId}")
//    public ResponseEntity<Boolean> deductAmount(@PathVariable String walletId, @RequestParam Float amount) throws ControllerException, ServiceException {
//        boolean success = walletService.deductAmount(walletId, amount);
//        return ResponseEntity.ok().body(success);
//    }
//
//    /**
//     * API endpoint for retrieving the balance of a wallet.
//     *
//     * @param walletId The ID of the wallet for which the balance is to be retrieved.
//     * @return ResponseEntity<Float> The balance of the specified wallet.
//     * @throws ControllerException If an error occurs during the balance retrieval process.
//     */
//    @GetMapping("/get-wallet-balance/{walletId}")
//    public ResponseEntity<Float> getWalletBalance(@PathVariable String walletId) throws ControllerException, ServiceException {
//        Float amount = walletService.getWalletBalance(walletId);
//        return ResponseEntity.ok().body(amount);
//    }
//
//}