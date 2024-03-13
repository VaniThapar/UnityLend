package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet getWalletInfo(String userId) throws ServiceException;

    void generateWallet(String userId) throws ServiceException;

    List<Wallet> getWallets() throws ServiceException;

    Wallet getWalletById(String walletId) throws ServiceException;

    void addAmountToWallet(String walletId, Float amount) throws ServiceException;

    void debitFromWallet(String walletId, Float amount) throws ServiceException;
}
