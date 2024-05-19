package com.dip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.exceptions.AmountExceededLimitException;
import com.dip.model.Wallets;
import com.dip.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dip/wallet")
@Slf4j
@CrossOrigin(origins = "*")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@GetMapping("/get-balance/{username}")
	public ResponseEntity<Integer> getCurrentWalletBalance(@RequestHeader(name = "Authorization", required = true) String auth, @PathVariable String username) {
		if(auth == null || auth.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		log.info("trying to fetch the wallet details with the username " + username);
		int balance =  walletService.getCurrentWalletBalanceByUsername(username);
		return new ResponseEntity<Integer>(balance, HttpStatus.OK);
	}
	
	@PostMapping("/add-balance/{username}")
	public ResponseEntity<Wallets> addWalletBalance(@RequestHeader(name = "Authorization", required = true) @PathVariable String username, @RequestBody Wallets wallet) throws AmountExceededLimitException {
		log.info("Initiating the process of adding wallet balance for the user with username " + username);
		Wallets newWallet = walletService.addWalletBalance(wallet, username);
		return new ResponseEntity<Wallets>(newWallet, HttpStatus.CREATED);
	}
	
	

}
