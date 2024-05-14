package com.dip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.model.Claims;
import com.dip.service.ClaimsService;

@RestController
@RequestMapping("/dip/claims")
public class ClaimsController {
	
	@Autowired
	private ClaimsService claimsService;
	
	@PostMapping("/submit-claim/{username}")
	public ResponseEntity<Claims> submitClaim(@RequestHeader("Authorization") @PathVariable String username, @RequestBody Claims claim) {
		Claims newClaim = claimsService.newClaim(claim, username);
		return new ResponseEntity<Claims>(newClaim, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-claims/{username}")
	public ResponseEntity<List<Claims>> getAllClaims(@RequestHeader("Authorization") @PathVariable String username) {
		List<Claims> list = claimsService.getClaimsHistory(username);
		return new ResponseEntity<List<Claims>>(list, HttpStatus.OK);
	}
	

}
