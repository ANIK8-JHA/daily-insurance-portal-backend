package com.dip.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.classes.Claim;
import com.dip.classes.Insurance;
import com.dip.exceptions.NotEnoughBalanceException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.UserPolicyClaim;
import com.dip.repository.UserPolicyClaimRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserPolicyClaimService {

	@Autowired
	private UserPolicyClaimRepository userPolicyClaimRepo;

	@Autowired
	private WalletService walletService;

	@Autowired
	private PolicyService policyService;

	public UserPolicyClaim purchasePolicy(Insurance insuranceObj, Long userId, String username)
			throws WalletNotFoundException, NotEnoughBalanceException {
		if (this.walletService.getCurrentWalletBalanceByUsername(username) < insuranceObj.getPremium()) {
			log.info("balance is less than the required balance");
			throw new NotEnoughBalanceException("Not enough wallet balance");
		}
		log.info("sufficent balance found : " + this.walletService.getCurrentWalletBalanceByUsername(username));
		UserPolicyClaim obj = new UserPolicyClaim();
		obj.setUserId(userId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedDateTime = dateTime.format(formatter);
		obj.setPurchaseDttm(formattedDateTime);
		obj.setPolicyId(policyService.getPolicyIdByName(insuranceObj.getPolicyName()));
		obj.setClaimStatus("Not Claimed");
		log.info("deducting premium balance : " + insuranceObj.getPremium());
		this.walletService.updateBalance(username, insuranceObj.getPremium());

		return this.userPolicyClaimRepo.save(obj);
	}

	public UserPolicyClaim newClaim(Claim claimObj) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedPurchaseDttm = claimObj.getPurchaseDttm().format(formatter);
		long policyId = policyService.getPolicyIdByName(claimObj.getPolicyName());
		UserPolicyClaim obj = userPolicyClaimRepo.findByPolicyIdAndDop(policyId, formattedPurchaseDttm);
		if (policyService.isUnderMaxCoverage(claimObj.getPolicyName(), claimObj.getClaimAmount()))
			obj.setClaimAmount(claimObj.getClaimAmount());
		obj.setClaimStatus("Claimed");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedDttm = dateTime.format(formatter);
		obj.setClaimDttm(formattedDttm);
		log.info(formattedDttm);
		return this.userPolicyClaimRepo.save(obj);
	}

	public List<UserPolicyClaim> getAllUserPolicyClaimsByUserId(Long userid) {
		return userPolicyClaimRepo.findByUserId(userid);
	}

}
