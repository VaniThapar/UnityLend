package com.educare.unitylend.service;


import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;

import java.util.List;

public interface WalletService {
    List<Wallet> getAllWallets() throws ServiceException;

    Wallet getWalletForUserId(String userId) throws ServiceException;

    Wallet getWalletForId(String walletId) throws ServiceException;

    String createWallet(String userId) throws ServiceException;

    boolean createUserWalletMap(String userId) throws ServiceException;

    boolean addAmount(String walletId, Float amount) throws ServiceException;

    boolean deductAmount(String walletId, Float amount) throws ServiceException;

    Float getWalletBalance(String userId) throws ServiceException;
}