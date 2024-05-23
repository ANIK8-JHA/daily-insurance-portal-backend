package com.dip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dip.exceptions.AmountExceededLimitException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.Wallets;
import com.dip.repository.WalletRepository;
 
@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
 
    @Mock
    private WalletRepository walletRepo;
 
    @InjectMocks
    private WalletService walletService;
 
    @Test
    public void testAddWalletBalanceSuccess() throws AmountExceededLimitException {
        Wallets wallet = new Wallets();
        wallet.setAddedBalance(500);
        wallet.setWalletType("TestType");
        
        Wallets currentWallet = new Wallets();
        currentWallet.setWalletBalance(500);
        currentWallet.setWalletType("TestType");
        currentWallet.setUsername("testUser");
 
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.emptyList());
        when(walletRepo.save(any(Wallets.class))).thenReturn(currentWallet);
 
        Wallets result = walletService.addWalletBalance(wallet, "testUser");
 
        assertNotNull(result);
        assertEquals(wallet.getAddedBalance(), result.getWalletBalance());
        assertEquals("testUser", result.getUsername());
        assertEquals("TestType", result.getWalletType());
 
        verify(walletRepo, times(1)).save(any(Wallets.class));
    }
 
    
    @Test
    public void testAddWalletBalanceThrowsAmountExceededLimitException() {
        Wallets wallet = new Wallets();
        wallet.setAddedBalance(95000);
 
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.emptyList());
 
        assertThrows(AmountExceededLimitException.class, () -> {
            walletService.addWalletBalance(wallet, "testUser");
        });
    }
 
    @Test
    public void testGetCurrentWalletBalanceByUsernameWhenWalletNotFound() {
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.emptyList());
 
        int balance = walletService.getCurrentWalletBalanceByUsername("testUser");
 
        assertEquals(0, balance);
    }
 
    @Test
    public void testGetCurrentWalletBalanceByUsernameWhenWalletFound() {
        Wallets wallet = new Wallets();
        wallet.setWalletBalance(1000);
 
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.singletonList(wallet));
        when(walletRepo.findWalletBalanceByUsername(anyString())).thenReturn(1000);
 
        int balance = walletService.getCurrentWalletBalanceByUsername("testUser");
 
        assertEquals(1000, balance);
    }
 
    @Test
    public void testUpdateBalanceSuccess() throws WalletNotFoundException {
        Wallets wallet = new Wallets();
        wallet.setWalletBalance(1000);
        wallet.setUsername("testUser");
 
        when(walletRepo.findLatesTransaction(anyString())).thenReturn(wallet);
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.singletonList(wallet));
        when(walletRepo.findWalletBalanceByUsername(anyString())).thenReturn(1000);
 
        walletService.updateBalance("testUser", 500);
 
        verify(walletRepo, times(1)).save(any(Wallets.class));
    }
 
    @Test
    public void testUpdateBalanceThrowsWalletNotFoundException() {
        when(walletRepo.findLatesTransaction(anyString())).thenReturn(null);
 
        assertThrows(WalletNotFoundException.class, () -> {
            walletService.updateBalance("testUser", 500);
        });
    }
 
    @Test
    public void testUpdateClaimBalanceInWalletSuccess() throws WalletNotFoundException {
        Wallets wallet = new Wallets();
        wallet.setWalletBalance(500);
        wallet.setUsername("testUser");
 
        when(walletRepo.findLatesTransaction(anyString())).thenReturn(wallet);
        when(walletRepo.findByUsername(anyString())).thenReturn(Collections.singletonList(wallet));
        when(walletRepo.findWalletBalanceByUsername(anyString())).thenReturn(500);
 
        walletService.updateClaimBalanceInWallet("testUser", 400);
 
        verify(walletRepo, times(1)).save(any(Wallets.class));
    }
 
    @Test
    public void testUpdateClaimBalanceInWalletThrowsWalletNotFoundException() {
        when(walletRepo.findLatesTransaction(anyString())).thenReturn(null);
 
        assertThrows(WalletNotFoundException.class, () -> {
            walletService.updateClaimBalanceInWallet("testUser", 400);
        });
    }
}