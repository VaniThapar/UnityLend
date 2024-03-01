package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.WalletRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService{
    WalletRepository walletRepository;


    @Override
    public List<Wallet> getWallets() throws ServiceException {
        try {
            List<Wallet> walletList = walletRepository.getAllWallets();
            log.info("walletList ", walletList);
            return walletList;
        } catch (Exception e) {
            log.error("Error encountered during wallet fetching operation");
            throw new ServiceException("Error encountered during wallet fetch operation", e);
        }
    }


    @Override
    public Wallet getWalletInfo(String userId) throws ServiceException {
        Wallet wallet = walletRepository.getWalletInfo(userId);


        User user = walletRepository.getUserIdWithWallet(wallet);
        wallet.setUser(user);
        return wallet;
    }


    @Override
    public void generateWallet(String userId) throws ServiceException {
        walletRepository.generateWalletInTable(userId);
        Wallet wallet = walletRepository.getWalletInfo(userId);
        User user = walletRepository.getUserIdWithWallet(wallet);
        wallet.setUser(user);
    }


    @Override
    public Wallet getWalletById(String walletId) {
        Wallet wallet = walletRepository.getWalletById(walletId);
        User user = walletRepository.getUserIdWithWallet(wallet);
        wallet.setUser(user);
        return wallet;
    }


    @Override
    @Transactional
    public void addAmountToWallet(String walletId, Float amount) {
        Wallet wallet = walletRepository.getWalletById(walletId);
        if (wallet != null) {
            Float currentBalance = wallet.getBalance();
            Float newBalance = currentBalance + amount;
            wallet.setBalance(newBalance);
            walletRepository.updateBalance(walletId, newBalance);
        } else {
            throw new RuntimeException("Wallet not found with id: " + walletId);
        }
    }


    @Override
    @Transactional
    public void debitFromWallet(String walletId, Float amount) {
        Wallet wallet = walletRepository.getWalletById(walletId);
        if (wallet != null) {
            Float currentBalance = wallet.getBalance();
            Float newBalance = currentBalance - amount;
            wallet.setBalance(newBalance);
            walletRepository.updateBalance(walletId, newBalance);
        } else {
            throw new RuntimeException("Wallet not found with id: " + walletId);
        }
    }
}
