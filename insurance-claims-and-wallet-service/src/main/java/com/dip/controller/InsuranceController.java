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

import com.dip.model.Insurances;
import com.dip.service.InsuranceService;

@RestController
@RequestMapping("/dip/insurance")
public class InsuranceController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@PostMapping("/buy-insurance/{username}")
	public Insurances buyInsurance(@RequestHeader("Authoriazation") @PathVariable String username, @RequestBody Insurances insurance) {
		return insuranceService.purchasePolicy(insurance, username);
	}
	
	@GetMapping("/get-insurances/{username}")
	public List<Insurances> getInsuranceHistory(@RequestHeader("Authoriazation") @PathVariable String username) {
		return insuranceService.InsuranceHistory(username);
	}
}
