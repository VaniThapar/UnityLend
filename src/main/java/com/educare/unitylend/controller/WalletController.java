package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {
    @GetMapping()
    public void getAllWallets() throws ControllerException {
    }

    @GetMapping()
    public void getWalletForUserId() throws ControllerException {
    }

    @GetMapping()
    public void getWalletForId() throws ControllerException {
    }

    @GetMapping()
    public void createWallet() throws ControllerException {
    }

    @GetMapping()
    public void createUserWalletMap() throws ControllerException {
    }

    @GetMapping()
    public void addAmount() throws ControllerException {
    }

    @GetMapping()
    public void deductAmount() throws ControllerException {
    }

    @GetMapping()
    public void getWalletAmount() throws ControllerException {
    }

}