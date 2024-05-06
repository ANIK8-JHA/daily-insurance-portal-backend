package com.dip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.model.Wallets;
import com.dip.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepo;
	
	public Wallets addWalletBalance(Wallets wallet, String username) {
		Wallets currentWallet;
		if(this.walletRepo.findByUsername(username) != null) {
			currentWallet = this.walletRepo.findByUsername(username);			
			currentWallet.setWalletBalance(currentWallet.getWalletBalance() + wallet.getWalletBalance());
		}else {
			currentWallet = new Wallets();
			currentWallet.setWalletBalance(wallet.getWalletBalance());
		}
		currentWallet.setusername(wallet.getusername());
		currentWallet.setWalletType(wallet.getWalletType());
		return walletRepo.save(currentWallet);
	}
	
	public int getCurrentWalletBalanceByUsername(String username) {
		if(this.walletRepo.findByUsername(username) == null) return 0;
		Wallets wallet = this.walletRepo.findByUsername(username);
		return wallet.getWalletBalance();
	}

}
