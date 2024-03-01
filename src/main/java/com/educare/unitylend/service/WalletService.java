package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;

public interface WalletService {

    Wallet getWalletInfo(String userId) throws ServiceException;

    void generateWallet(String userId) throws ServiceException;


}
