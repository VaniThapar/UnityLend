package com.educare.unitylend.service;


import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    List<Wallet> getAllWallets() throws ServiceException;

    Wallet getWalletForUserId(String userId) throws ServiceException;

    Wallet getWalletForWalletId(String walletId) throws ServiceException;

//    Boolean createUserWalletMap(String userId) throws ServiceException;

    Boolean addAmount(String walletId, BigDecimal amount) throws ServiceException;

    Boolean deductAmount(String walletId, BigDecimal amount) throws ServiceException;

    BigDecimal getWalletBalance(String userId) throws ServiceException;
}