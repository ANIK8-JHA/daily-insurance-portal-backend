package com.dip.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dip.classes.Claim;
import com.dip.classes.Insurance;
import com.dip.exceptions.NotEnoughBalanceException;
import com.dip.exceptions.WalletNotFoundException;

@SpringBootTest
public class UserPolicyClaimTest {
	
	@Autowired
	private UserPolicyClaimService userPolicyClaimService;
	
	@Test
	public void testPurchasePolicy() throws WalletNotFoundException, NotEnoughBalanceException {
		Insurance insuranceObj = new Insurance();
		insuranceObj.setPolicyName("Policy2");
		insuranceObj.setPremium(1500);
		assertNotNull(userPolicyClaimService.purchasePolicy(insuranceObj, (long) 2, "anik8-jha"));
	}
	
	@Test
	public void testGetAllUserPolicyClaimsByUserId() {
		assertNotNull(userPolicyClaimService.getAllUserPolicyClaimsByUserId((long) 2));
	}
	
	@Test
	public void testNewClaim() {
		String timeString = "2024-05-19 21:10";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);
		
		Claim claimObj = new Claim();
		claimObj.setClaimAmount(5000);
		claimObj.setPolicyName("Policy1");
		claimObj.setPurchaseDttm(dateTime);
		
		assertNotNull(userPolicyClaimService.newClaim(claimObj));
		
	}
	

}
