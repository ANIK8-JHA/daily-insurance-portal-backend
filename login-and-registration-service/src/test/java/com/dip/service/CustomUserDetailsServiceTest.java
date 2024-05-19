package com.dip.service;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomUserDetailsServiceTest {
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Test
	public void testLoadUserByUsername() {
		String username = "anik8-jha";
		
		assertNotNull(service.loadUserByUsername(username));
		
	}

}
