package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    @Override
    public List<Wallet> getAllWallets() throws ServiceException {
        return null;
    }

    @Override
    public Wallet getWalletForUserId(String userId) throws ServiceException {
        return null;
    }

    @Override
    public Wallet getWalletForId(String walletId) throws ServiceException {
        return null;
    }

    @Override
    public String createWallet(String userId) throws ServiceException {
        return null;
    }

    @Override
    public boolean createUserWalletMap(String userId) throws ServiceException {
        return false;
    }

    @Override
    public boolean addAmount(String walletId, Float amount) throws ServiceException {
        return false;
    }

    @Override
    public boolean deductAmount(String walletId, Float amount) throws ServiceException {
        return false;
    }

    @Override
    public Float getWalletAmount(String userId) throws ServiceException {
        return null;
    }
}
