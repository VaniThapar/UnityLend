package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.WalletRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService{

    WalletRepository walletRepository;

    @Override
    public Wallet getWalletInfo(String userId) throws ServiceException {
        Wallet wallet = walletRepository.getWalletInfo(userId);
        return wallet;
    }

    @Override
    public void generateWallet(String userId) throws ServiceException {
        walletRepository.generateWalletInTable(userId);
        Wallet wallet = walletRepository.getWalletInfo(userId);
        User user = walletRepository.getUserIdWithWallet(wallet);
        wallet.setUser(user);
    }


}
