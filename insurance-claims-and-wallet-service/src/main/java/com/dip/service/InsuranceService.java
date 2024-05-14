package com.dip.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.exceptions.NotEnoughBalanceException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.Insurances;
import com.dip.repository.InsuranceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InsuranceService {
	
	@Autowired
	private InsuranceRepository insuranceRepo;
	
	@Autowired
	private WalletService walletService;
	
	
	public Insurances purchasePolicy(Insurances policy, String username) throws WalletNotFoundException, NotEnoughBalanceException {
		log.info("checking if user has sufficient wallet balance or not");
		if(this.walletService.getCurrentWalletBalanceByUsername(username) < policy.getPremium()) {
			log.info("balance is less than the required balance");
			throw new NotEnoughBalanceException("Not enough wallet balance");
		}
		log.info("sufficent balance found : " + this.walletService.getCurrentWalletBalanceByUsername(username));
		policy.setUsername(username);
		policy.setPurchaseDate(new Date());
		this.walletService.updateBalance(username, policy.getPremium());
		return this.insuranceRepo.save(policy);		
	}
	
	public List<Insurances> InsuranceHistory(String username) {
		return this.insuranceRepo.findAllByUsername(username);
	}
	
	public Date getDateOfPurchaseByInsurancename(String username, String insuranceName) {
		if(this.insuranceRepo.findDateByUsernameAndInsuranceName(username, insuranceName) == null) {
			log.info("Date not found with the username : " + username + "and insuranceName : " + insuranceName);
		}
		return this.insuranceRepo.findDateByUsernameAndInsuranceName(username, insuranceName);
	}

}
