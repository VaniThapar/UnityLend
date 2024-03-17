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
    @Override
    public List<Wallet> getAllWallets() throws ServiceException {
        try{
            List<Wallet>walletList=walletRepository.getAllWallets();
            log.info("wallet list:: "+walletList);

            for(Wallet wallet:walletList){
                User user=userService.getUserByWalletId(wallet.getWalletId());
                wallet.setUser(user);
            }

            return walletList;
        }
        catch(Exception e){
            log.error("Error encountered in getting all wallets");
            throw new ServiceException("Error encountered in getting all wallets",e);
        }
    }

    @Override
    public Wallet getWalletForUserId(String userId) throws ServiceException {
        try{
           Wallet requiredWallet=walletRepository.getWalletForUserId(userId);
           User user=userService.getUserForUserId(userId);
           requiredWallet.setUser(user);
           return requiredWallet;
        }
        catch(Exception e){
            log.error("Error encountered in getting wallet by user id");
            throw new ServiceException("Error encountered in getting wallet by user id",e);
        }
    }

    @Override
    public Wallet getWalletForWalletId(String walletId) throws ServiceException {
        try{
            Wallet requiredWallet=walletRepository.getWalletForWalletId(walletId);
            User user=userService.getUserByWalletId(walletId);
            requiredWallet.setUser(user);
            return requiredWallet;
        }
        catch(Exception e){
            log.error("Error encountered in getting wallet by wallet id");
            throw new ServiceException("Error encountered in getting wallet by wallet id",e);
        }
    }

    @Override
    public Boolean addAmount(String walletId, BigDecimal amount) throws ServiceException {
        try{
            Boolean isAdded=walletRepository.addAmount(walletId,amount);
            return isAdded;
        }
        catch(Exception e){
            log.error("Error encountered in adding amount to wallet");
            throw new ServiceException("Error encountered in adding amount to wallet",e);
        }
    }

    @Override
    public Boolean deductAmount(String walletId, BigDecimal amount) throws ServiceException {
        try{
            Boolean isDeducted=walletRepository.deductAmount(walletId,amount);
            return isDeducted;
        }
        catch(Exception e){
            log.error("Error encountered in deducting amount from wallet");
            throw new ServiceException("Error encountered in deducting amount from wallet",e);
        }
    }

    @Override
    public BigDecimal getWalletBalance(String userId) throws ServiceException {
        try{
            BigDecimal balance=walletRepository.getWalletBalance(userId);
            log.info("balance:: "+balance);
            return balance;
        }
        catch(Exception e){
            log.error("Error encountered in getting wallet balance");
            throw new ServiceException("Error encountered in getting wallet balance",e);
        }
    }
}
