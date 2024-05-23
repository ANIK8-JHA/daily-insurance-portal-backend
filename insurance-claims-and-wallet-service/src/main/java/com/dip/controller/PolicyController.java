package com.dip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.model.Policy;
import com.dip.service.PolicyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dip/policy")
@Slf4j
@CrossOrigin(origins = "*")
public class PolicyController {
	
	@Autowired
	private PolicyService policyService;
	
	@GetMapping("/get-all-policies")
	public ResponseEntity<List<Policy>> getAllPolicies(@RequestHeader(name = "Authorization", required = false) String auth) {
		return new ResponseEntity<List<Policy>>(policyService.getAllPolicies(), HttpStatus.OK);
	}
	
	@GetMapping("/get-policy-name/{policyId}")
	public ResponseEntity<String> getPolicyNameById(@RequestHeader(name = "Authorization", required = false) String auth, @PathVariable long policyId) {
		return new ResponseEntity<String>(policyService.getPolicyNameById(policyId), HttpStatus.OK);
	}

}
