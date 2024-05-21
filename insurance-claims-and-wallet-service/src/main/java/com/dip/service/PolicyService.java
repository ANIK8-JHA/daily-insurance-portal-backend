package com.dip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dip.model.Policy;
import com.dip.repository.PolicyRepository;

@Service
public class PolicyService {
	
	@Autowired
	private PolicyRepository policyRepo;
	
	public long getPolicyIdByName(String policyName) {
		return policyRepo.findByPolicyName(policyName).getPolicyId();
	}
	
	public String getPolicyNameById(long policyId) {
		Policy policy = policyRepo.findById(policyId).orElse(null);
		if(policy == null) return null;
		return policy.getPolicyName();
	}
	
	public List<String> getAllPolicyNames() {
		return policyRepo.findAllPolicyName();
	}
	
	public List<Policy> getAllPolicies() {
		return policyRepo.findAll();
	}
	
	public Boolean isUnderMaxCoverage(String policyName, int requestedCoverage) {
		Policy policy = policyRepo.findByPolicyName(policyName);
		if(requestedCoverage > policy.getPolicyCoverage()) return false;
		return true;
	}
	
	public Policy findByPolicyName(String policyName) {
		return policyRepo.findByPolicyName(policyName);
	}
}
