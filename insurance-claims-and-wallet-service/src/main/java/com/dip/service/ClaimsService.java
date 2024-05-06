package com.dip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.model.Claims;
import com.dip.repository.ClaimsRepository;
import com.dip.repository.InsuranceRepository;

@Service
public class ClaimsService {
	
	@Autowired
	private ClaimsRepository claimsRepo;
	
	@Autowired
	private InsuranceRepository insuranceRepo;
	
	public Claims newClaim(Claims claim, String username) {
		claim.setDateOfPurchase(insuranceRepo.findByUsernameAndInsuranceName(username, claim.getPolicyName()));
		claim.setUsername(username);
		return claimsRepo.save(claim);
	}
	
	public List<Claims> getClaimsHistory(String username) {
		return claimsRepo.findAllByUsername(username);
	}
}
