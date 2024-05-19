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

import com.dip.classes.Insurance;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dip/insurance")
@Slf4j
@CrossOrigin(origins = "*")
public class InsuranceController {
	
	@Autowired
	private UserPolicyClaimService insuranceService;
	
	@PostMapping("/buy-insurance/{username}/{userId}")
	public ResponseEntity<UserPolicyClaim> buyInsurance(@RequestHeader("Authoriazation") @PathVariable String username, @PathVariable long userId, @RequestBody Insurance insurance) throws Exception {
		log.info("Initiating buying process");
		UserPolicyClaim newInsurance =  insuranceService.purchasePolicy(insurance, userId, username);
		return new ResponseEntity<UserPolicyClaim>(newInsurance, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-insurances/{userId}")
	public ResponseEntity<List<UserPolicyClaim>> getUserPolicyClaimHistory(@RequestHeader("Authoriazation") @PathVariable long userId) {
		List<UserPolicyClaim> list = insuranceService.getAllUserPolicyClaimsByUserId(userId);
		return new ResponseEntity<List<UserPolicyClaim>>(list, HttpStatus.OK);
	}
}
