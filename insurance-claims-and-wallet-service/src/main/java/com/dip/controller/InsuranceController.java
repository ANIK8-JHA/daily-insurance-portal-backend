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

import com.dip.model.Insurances;
import com.dip.service.InsuranceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dip/insurance")
@Slf4j
public class InsuranceController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@PostMapping("/buy-insurance/{username}")
	public ResponseEntity<Insurances> buyInsurance(@RequestHeader("Authoriazation") @PathVariable String username, @RequestBody Insurances insurance) throws Exception {
		log.info("Initiating buying process");
		Insurances newInsurance =  insuranceService.purchasePolicy(insurance, username);
		return new ResponseEntity<Insurances>(newInsurance, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-insurances/{username}")
	public ResponseEntity<List<Insurances>> getInsuranceHistory(@RequestHeader("Authoriazation") @PathVariable String username) {
		List<Insurances> list = insuranceService.InsuranceHistory(username);
		return new ResponseEntity<List<Insurances>>(list, HttpStatus.OK);
	}
}
