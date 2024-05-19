package com.dip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.dip.model.Wallets;
import com.dip.service.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @Mock
    private Wallets wallet;

    @Mock
    private ResponseEntity<Wallets> responseEntity;

    @InjectMocks
    private WalletController walletController;

    @Test
    public void testAddWalletBalance() throws Exception {
    	String username = "anik8-jha";
        Wallets requestWallet = new Wallets();
        requestWallet.setAddedBalance(100);
        requestWallet.setWalletType("UPI");

        ResponseEntity<Wallets> response = walletController.addWalletBalance(username, requestWallet);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
    
    @Test
    public void testGetCurrentWalletBalance() {
    	String username = "user";
        int balance = 50;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer token");

        Mockito.when(walletService.getCurrentWalletBalanceByUsername(username)).thenReturn(balance);

        // Act
        ResponseEntity<Integer> response = walletController.getCurrentWalletBalance(headers.toString(), username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
