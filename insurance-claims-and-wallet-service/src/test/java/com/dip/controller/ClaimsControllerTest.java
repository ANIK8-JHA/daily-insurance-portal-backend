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

import com.dip.classes.Claim;
import com.dip.exceptions.ClaimAmountGreaterThanCoverageAmountException;
import com.dip.exceptions.PolicyAlreadyClaimedException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;

@SpringBootTest
public class ClaimsControllerTest {

	@Mock
	private UserPolicyClaimService claimsService;

	@Mock
	private Claim claimObj;

	@Mock
	private UserPolicyClaim userPolicyClaimObj;

	@InjectMocks
	private ClaimsController claimsController;

	@Test
	public void testSubmitClaim() throws ClaimAmountGreaterThanCoverageAmountException, WalletNotFoundException, PolicyAlreadyClaimedException {
		Claim claim = new Claim();
		claim.setClaimAmount(5000);
		claim.setPolicyName("Policy1");
		UserPolicyClaim obj = new UserPolicyClaim();
		obj.setClaimAmount(claim.getClaimAmount());
		obj.setClaimStatus("Claimed");

		Mockito.when(claimsService.newClaim(claim, null)).thenReturn(obj);

		ResponseEntity<UserPolicyClaim> response = claimsController.submitClaim(null, claim);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testGetAllUserPolicyClaimsByUserId() {
		ResponseEntity<List<UserPolicyClaim>> response = claimsController.getAllUserPolicyClaimsByUserId(2L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
