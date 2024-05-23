package com.dip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.classes.Claim;
import com.dip.exceptions.ClaimAmountGreaterThanCoverageAmountException;
import com.dip.exceptions.PolicyAlreadyClaimedException;
import com.dip.exceptions.SubmittingClaimOnTheSameDayException;
import com.dip.exceptions.WalletNotFoundException;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;

@RestController
@RequestMapping("/dip/claims")
@CrossOrigin(origins = "*")
public class ClaimsController {

	@Autowired
	private UserPolicyClaimService claimsService;

	@PostMapping("/submit-claim/{username}")
	public ResponseEntity<UserPolicyClaim> submitClaim(@RequestHeader(name = "Authorization", required = false) String auth, @PathVariable String username, @RequestBody Claim claim) throws ClaimAmountGreaterThanCoverageAmountException, WalletNotFoundException, PolicyAlreadyClaimedException, SubmittingClaimOnTheSameDayException {
		UserPolicyClaim newClaim = claimsService.newClaim(claim, username);
		return new ResponseEntity<UserPolicyClaim>(newClaim, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all-by/{userId}")
	public ResponseEntity<List<UserPolicyClaim>> getAllUserPolicyClaimsByUserId(@RequestHeader(name = "Authorization", required = false) String auth, @PathVariable long userId) {
		List<UserPolicyClaim> obj = claimsService.getAllUserPolicyClaimsByUserId(userId);
		return new ResponseEntity<List<UserPolicyClaim>>(obj, HttpStatus.OK);
	}

}
