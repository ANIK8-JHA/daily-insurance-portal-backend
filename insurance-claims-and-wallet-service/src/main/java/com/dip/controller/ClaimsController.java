package com.dip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.classes.Claim;
import com.dip.model.UserPolicyClaim;
import com.dip.service.PolicyService;
import com.dip.service.UserPolicyClaimService;

@RestController
@RequestMapping("/dip/claims")
@CrossOrigin(origins = "*")
public class ClaimsController {

	@Autowired
	private UserPolicyClaimService claimsService;
	
	@Autowired
	private PolicyService policyService;

	@PostMapping("/submit-claim")
	public ResponseEntity<UserPolicyClaim> submitClaim(@RequestHeader("Authorization") @RequestBody Claim claim) {
		UserPolicyClaim newClaim = claimsService.newClaim(claim);
		return new ResponseEntity<UserPolicyClaim>(newClaim, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all-by/{userId}")
	public ResponseEntity<List<UserPolicyClaim>> getAllUserPolicyClaimsByUserId(@RequestHeader("Authorization") @PathVariable long userId) {
		List<UserPolicyClaim> obj = claimsService.getAllUserPolicyClaimsByUserId(userId);
		return new ResponseEntity<List<UserPolicyClaim>>(obj, HttpStatus.OK);
	}

//	@GetMapping("/get-claims/{username}")
//	public ResponseEntity<List<Claims>> getAllClaims(@RequestHeader("Authorization") @PathVariable String username) {
//		List<Claims> list = claimsService.getClaimsHistory(username);
//		return new ResponseEntity<List<Claims>>(list, HttpStatus.OK);
//	}

}
