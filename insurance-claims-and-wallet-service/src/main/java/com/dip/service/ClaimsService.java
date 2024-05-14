package com.dip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.model.Claims;
import com.dip.repository.ClaimsRepository;
import com.dip.repository.InsuranceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClaimsService {
	
	@Autowired
	private ClaimsRepository claimsRepo;
	
	@Autowired
	private InsuranceService insuranceService;
	
	public Claims newClaim(Claims claim, String username) {
//		log.info(insuranceService.getDateOfPurchaseByInsurancename(username, claim.getPolicyName()).toString());
		claim.setDateOfPurchase(insuranceService.getDateOfPurchaseByInsurancename(username, claim.getPolicyName()));
		claim.setUsername(username);
		return claimsRepo.save(claim);
	}
	
	public List<Claims> getClaimsHistory(String username) {
		return claimsRepo.findAllByUsername(username);
	}
}
