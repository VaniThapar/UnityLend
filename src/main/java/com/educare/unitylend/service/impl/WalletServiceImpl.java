package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.controller.UserCommunityController;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.dao.UserCommunityRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.dao.WalletRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.UserService;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService{

    WalletRepository walletRepository;

    @Override
    public Wallet getWalletInfo(String userId) throws ServiceException {
        return walletRepository.getWalletInfo(userId);
    }

    @Override
    public void generateWallet(Wallet wallet) throws ServiceException {
         walletRepository.generateWallet(wallet);
    }


}
