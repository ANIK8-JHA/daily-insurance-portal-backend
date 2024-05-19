package com.dip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.Wallets;
import com.dip.repository.WalletRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletService {

	@Autowired
	private WalletRepository walletRepo;

	public Wallets addWalletBalance(Wallets wallet, String username) {
		Wallets currentWallet = new Wallets();
		int currentWalletBalance = this.getCurrentWalletBalanceByUsername(username);			
		currentWallet.setWalletBalance(currentWalletBalance + wallet.getAddedBalance());
		currentWallet.setUsername(username);
		currentWallet.setWalletType(wallet.getWalletType());
		currentWallet.setAddedBalance(wallet.getAddedBalance());

		return walletRepo.save(currentWallet);
	}
 
	public int getCurrentWalletBalanceByUsername(String username) {
		if (walletRepo.findByUsername(username).size() == 0) {
			log.info("Wallet details not found with the username " + username);
			log.info("setting wallet balance to 0");
			return 0;
		}else {
			log.info("wallet not giving null value, even though it should");
			log.info(walletRepo.findByUsername(username).toString());
		}
			
		return walletRepo.findWalletBalanceByUsername(username);
	}
	
	public void updateBalance(String username, int balance) throws WalletNotFoundException {
		Wallets latestWallet = walletRepo.findLatesTransaction(username);
		if(latestWallet == null) throw new WalletNotFoundException("No wallet data found");
		else log.info("Wallet found");
		log.info("Updating Wallet Balance");
		log.info("current wallet balance" + this.getCurrentWalletBalanceByUsername(username));
		log.info("Deducted Balance" + balance);
		Wallets newWallet = new Wallets();
		newWallet.setWalletBalance(this.getCurrentWalletBalanceByUsername(username) - balance);
		newWallet.setUsername(username);
		newWallet.setAddedBalance(0);
		this.walletRepo.save(newWallet);
		log.info("new wallet");
		log.info(newWallet.toString());
	}

}
