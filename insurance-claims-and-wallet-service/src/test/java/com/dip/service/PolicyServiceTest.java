package com.dip.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dip.repository.PolicyRepository;

@SpringBootTest
public class PolicyServiceTest {
	
	@Autowired
	private PolicyService policyService;
	
	@Test
	public void testGetAllPolicies() {
		assertNotNull(policyService.getAllPolicies());
	}
	
	@Test
	public void testGetAllPolicyNames() {
		assertNotNull(policyService.getAllPolicyNames());
	}
	
	@Test
	public void testGetPolicyNameById() {
		assertNotNull(policyService.getPolicyNameById((long) 1));
	}
	
	@Test
	public void testGetPolicyIdByName() {
		assertNotNull(policyService.getPolicyIdByName("Policy1"));
	}
	
	@Test
	public void testIsUnderMaxCoverage() {
		assertTrue(policyService.isUnderMaxCoverage("Policy1", 5000));
	}
	

}
