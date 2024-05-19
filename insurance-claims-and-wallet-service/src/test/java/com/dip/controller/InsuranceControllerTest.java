package com.dip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dip.classes.Insurance;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;

@SpringBootTest
public class InsuranceControllerTest {
	
	@Mock
	private UserPolicyClaimService insuranceService;
	
	@Mock
	private Insurance insuranceObj;
	
	@Mock
	private UserPolicyClaim userPolicyClaimObj;
	
	@InjectMocks
	private InsuranceController insuranceController;
	
	@Test
	public void testBuyInsurance() throws Exception {
		long userId = 2L;
		String username = "anik8-jha";
		Insurance insurance = new Insurance();
		insurance.setPolicyName("Policy1");
		insurance.setPremium(1000);
		
		UserPolicyClaim obj = new UserPolicyClaim();
		obj.setUserId(userId);
		obj.setPolicyId(1L);
		
		Mockito.when(insuranceService.purchasePolicy(insurance, userId, username)).thenReturn(obj);
		
		ResponseEntity<UserPolicyClaim> response = insuranceController.buyInsurance(username, userId, insurance);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
	}
	
	@Test
	public void testGetUserPolicyClaimHistory() {
		ResponseEntity<List<UserPolicyClaim>> response = insuranceController.getUserPolicyClaimHistory(2L);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
