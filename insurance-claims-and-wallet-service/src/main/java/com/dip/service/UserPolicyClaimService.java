package com.dip.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.classes.Claim;
import com.dip.classes.Insurance;
import com.dip.exceptions.BuyingPolicyOnTheSameDayException;
import com.dip.exceptions.ClaimAmountGreaterThanCoverageAmountException;
import com.dip.exceptions.NotEnoughBalanceException;
import com.dip.exceptions.PolicyAlreadyClaimedException;
import com.dip.exceptions.SubmittingClaimOnTheSameDayException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.Policy;
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
			throws WalletNotFoundException, NotEnoughBalanceException, BuyingPolicyOnTheSameDayException {
		if(this.isBuyingOnSameDay(userId)) {
			log.info("Submitting on the same day");
			throw new BuyingPolicyOnTheSameDayException("You can't buy two insurance Policies on the same day");
		}else {
			log.info("Not submitting on the same day");
		}
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

	public UserPolicyClaim newClaim(Claim claimObj, String username) throws ClaimAmountGreaterThanCoverageAmountException, WalletNotFoundException, PolicyAlreadyClaimedException, SubmittingClaimOnTheSameDayException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedPurchaseDttm = claimObj.getPurchaseDttm().format(formatter);
		long policyId = policyService.getPolicyIdByName(claimObj.getPolicyName());
		Policy policy = policyService.findByPolicyName(claimObj.getPolicyName());
		UserPolicyClaim obj = userPolicyClaimRepo.findByPolicyIdAndDop(policyId, formattedPurchaseDttm);
		
		if(this.isClaimingOnSameDay(obj)) {
			log.info("Claiming on the smae day");
			throw new SubmittingClaimOnTheSameDayException("You can't claim on the same day as the date of purchase");
		}
		
		if (!policyService.isUnderMaxCoverage(claimObj.getPolicyName(), claimObj.getClaimAmount()))
			throw new ClaimAmountGreaterThanCoverageAmountException("Please select an amount below " + policy.getPolicyCoverage());
		log.info(String.valueOf(claimObj.getClaimAmount()));
		log.info(obj.toString());
		
		if(this.isClaimed(obj)) 
			throw new PolicyAlreadyClaimedException("Policy purchased on selected date and time is already claimed");
		obj.setClaimAmount(claimObj.getClaimAmount());
		obj.setClaimStatus("Claimed");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedDttm = dateTime.format(formatter);
		obj.setClaimDttm(formattedDttm);
		log.info(formattedDttm);
		this.walletService.updateClaimBalanceInWallet(username, policy.getPolicyPremium());
		return this.userPolicyClaimRepo.save(obj);
	}

	public List<UserPolicyClaim> getAllUserPolicyClaimsByUserId(Long userid) {
		return userPolicyClaimRepo.findByUserId(userid);
	}
	
	public Boolean isClaimed(UserPolicyClaim obj) {
		String status = obj.getClaimStatus();
		if(status.equalsIgnoreCase("Not Claimed")) {
			log.info(obj.getClaimStatus());
			return false;
		}else {
			log.info("Policy is ");
			log.info(status);
		}
		return true;
	}
	
	public Boolean isBuyingOnSameDay(long userId) {
		UserPolicyClaim latestInsurance = userPolicyClaimRepo.findLatestByUserId(userId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDate = LocalDateTime.now();
		String current = currentDate.format(formatter);
		String purchaseDate = latestInsurance.getPurchaseDttm().substring(0, 10);
		log.info("Required Data is below this");
		log.info(purchaseDate);
		log.info(current);
		if(purchaseDate.equals(current)) {
			log.info("Submitting on the same day");
			return true;
		}
		log.info("current date");
		log.info(current);
		log.info("Purchase date");
		log.info(purchaseDate);
		return false;
	}
	
	public Boolean isClaimingOnSameDay(UserPolicyClaim obj) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDate = LocalDateTime.now();
		String current = currentDate.format(formatter);
		String purchaseDate = obj.getPurchaseDttm().substring(0, 10);
		if(purchaseDate.equals(current)) {
			log.info("Submitting on the same day");
			return true;
		}
		log.info("current date");
		log.info(current);
		log.info("Purchase date");
		log.info(purchaseDate);
		return false;
	}

}
