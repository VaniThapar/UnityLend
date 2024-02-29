package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet getWalletInfo(String userId) throws ServiceException;

    void generateWallet(Wallet wallet) throws ServiceException;

}
