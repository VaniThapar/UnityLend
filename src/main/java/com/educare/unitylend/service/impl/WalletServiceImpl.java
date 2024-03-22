package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.WalletRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.UserService;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;
    private UserService userService;

    /**
     * Retrieves a list of all wallets along with their associated users.
     *
     * @return A list containing all wallets with their associated users.
     * @throws ServiceException If an error occurs while retrieving the wallets.
     */
    @Override
    public List<Wallet> getAllWallets() throws ServiceException {
        try {
            List<Wallet> walletList = walletRepository.getAllWallets();

            for (Wallet wallet : walletList) {
                User user = userService.getUserByWalletId(wallet.getWalletId());
                wallet.setUser(user);
            }

            return walletList;
        } catch (Exception e) {
            log.error("Error encountered in getting all wallets");
            throw new ServiceException("Error encountered in getting all wallets", e);
        }
    }


    /**
     * Retrieves the wallet associated with the specified user ID.
     *
     * @param userId The ID of the user.
     * @return The wallet associated with the specified user ID.
     * @throws ServiceException If an error occurs while retrieving the wallet.
     */
    @Override
    public Wallet getWalletForUserId(String userId) throws ServiceException {
        try {
            Wallet requiredWallet = walletRepository.getWalletForUserId(userId);
            User user = userService.getUserForUserId(userId);
            requiredWallet.setUser(user);
            return requiredWallet;
        } catch (Exception e) {
            log.error("Error encountered in getting wallet by user id");
            throw new ServiceException("Error encountered in getting wallet by user id", e);
        }
    }


    /**
     * Retrieves the wallet associated with the specified wallet ID.
     *
     * @param walletId The ID of the wallet.
     * @return The wallet associated with the specified wallet ID.
     * @throws ServiceException If an error occurs while retrieving the wallet.
     */
    @Override
    public Wallet getWalletForWalletId(String walletId) throws ServiceException {
        try {
            Wallet requiredWallet = walletRepository.getWalletForWalletId(walletId);
            User user = userService.getUserByWalletId(walletId);
            requiredWallet.setUser(user);
            return requiredWallet;
        } catch (Exception e) {
            log.error("Error encountered in getting wallet by wallet id");
            throw new ServiceException("Error encountered in getting wallet by wallet id", e);
        }
    }


    /**
     * Adds the specified amount to the wallet associated with the given wallet ID.
     *
     * @param walletId The ID of the wallet.
     * @param amount The amount to add to the wallet.
     * @return True if the amount was successfully added to the wallet, false otherwise.
     * @throws ServiceException If an error occurs while adding the amount to the wallet.
     */

    @Override
    public Boolean addAmount(String walletId, BigDecimal amount) throws ServiceException {
        try {
            Boolean isAdded = walletRepository.addAmount(walletId, amount);
            return isAdded;
        } catch (Exception e) {
            log.error("Error encountered in adding amount to wallet");
            throw new ServiceException("Error encountered in adding amount to wallet", e);
        }
    }


/**
 * Deducts the specified amount from the wallet associated with the given wallet ID.
 *
 * @param walletId The ID of the wallet.
 * @param amount The amount to deduct from the wallet.
 * @return True if the amount was successfully deducted from the wallet, false otherwise.
 * @throws ServiceException If an error occurs while deducting the amount from the wallet.
 */

    @Override
    public Boolean deductAmount(String walletId, BigDecimal amount) throws ServiceException {
        try {
            Wallet wallet=getWalletForWalletId(walletId);
            BigDecimal balance=wallet.getBalance();
            if(balance.compareTo(amount)<0){
                log.error("Insufficient Balance! Cannot deduct from wallet");
                throw new Exception("Insufficient Balance! Cannot deduct from wallet");
            }
            Boolean isDeducted = walletRepository.deductAmount(walletId, amount);
            return isDeducted;
        } catch (Exception e) {
            log.error("Error encountered in deducting amount from wallet");
            throw new ServiceException("Error encountered in deducting amount from wallet", e);
        }
    }

    /**
     * Retrieves the balance of the wallet associated with the given user ID.
     *
     * @param userId The ID of the user whose wallet balance is to be retrieved.
     * @return The balance of the wallet associated with the specified user ID.
     * @throws ServiceException If an error occurs while retrieving the wallet balance.
     */

    @Override
    public BigDecimal getWalletBalance(String userId) throws ServiceException {
        try {
            BigDecimal balance = walletRepository.getWalletBalance(userId);
            return balance;
        } catch (Exception e) {
            log.error("Error encountered in getting wallet balance");
            throw new ServiceException("Error encountered in getting wallet balance", e);
        }
    }
}
