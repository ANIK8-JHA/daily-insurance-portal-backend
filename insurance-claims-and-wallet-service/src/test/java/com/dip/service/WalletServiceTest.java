package com.dip.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dip.exceptions.AmountExceededLimitException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.Wallets;
import com.dip.repository.WalletRepository;

@SpringBootTest
public class WalletServiceTest {
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private WalletRepository walletRepo;
	
	@Test
	public void testAddWalletBalance() throws AmountExceededLimitException {
		Wallets newWallet = new Wallets();
		newWallet.setWalletType("UPI");
		newWallet.setAddedBalance(3000);
		assertNotNull(walletService.addWalletBalance(newWallet, "anik8-jha"));
	}
	
	@Test
	public void testUpdateBalance() throws WalletNotFoundException {
		walletService.updateBalance("anik8-jha", 1000);
		Wallets wallet = walletRepo.findLatesTransaction("anik8-jha");
		assertEquals(0, wallet.getAddedBalance());
	}

}
