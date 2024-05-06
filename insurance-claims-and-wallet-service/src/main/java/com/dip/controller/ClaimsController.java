package com.dip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Claims submitClaim(@RequestHeader("Authorization") @PathVariable String usrname, @RequestBody Claims claim) {
		return claimsService.newClaim(claim, usrname);
	}
	
	@GetMapping("/get-claims/{username}")
	public List<Claims> getAllClaims(@RequestHeader("Authorization") @PathVariable String username) {
		return claimsService.getClaimsHistory(username);
	}
	

}
