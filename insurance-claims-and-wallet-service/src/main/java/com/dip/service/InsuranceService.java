package com.dip.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.model.Insurances;
import com.dip.repository.InsuranceRepository;

@Service
public class InsuranceService {
	
	@Autowired
	private InsuranceRepository insuranceRepo;
	
	public Insurances purchasePolicy(Insurances policy, String username) {
		policy.setUsername(username);
		policy.setPuchaseDate(new Date());
		return insuranceRepo.save(policy);		
	}
	
	public List<Insurances> InsuranceHistory(String username) {
		return insuranceRepo.findAllByUsername(username);
	}
	
	public Date getDateOfPurchaseByInsurancename(String insuranceName, String username) {
		return insuranceRepo.findByUsernameAndInsuranceName(username, insuranceName);
	}

}
