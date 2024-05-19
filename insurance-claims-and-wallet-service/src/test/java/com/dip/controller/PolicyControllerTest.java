package com.dip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dip.model.Policy;

@SpringBootTest
public class PolicyControllerTest {
	@Autowired
	private PolicyController policyController;
	
	@Test
	public void testGetAllPolicies() {
		ResponseEntity<List<Policy>> response = policyController.getAllPolicies();
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetPolicyNameById() {
		ResponseEntity<String> response = policyController.getPolicyNameById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
