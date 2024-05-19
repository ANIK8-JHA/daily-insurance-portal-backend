package com.dip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dip.exceptions.UserNotFoundException;
import com.dip.model.JwtRequest;
import com.dip.model.JwtResponse;
import com.dip.model.User;
import com.dip.service.CustomUserDetailsService;

@SpringBootTest
public class AuthControllerTest {
	
	@Autowired
	private AuthController controller;
	
	@Test
	public void testLogin() throws UserNotFoundException {		
		JwtRequest reqObj = new JwtRequest();
		reqObj.setUsername("anik8-jha");
		reqObj.setPassword("123");
		
		ResponseEntity<JwtResponse> response = controller.login(reqObj);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());	
		
	}
	
	@Test
	public void testCreateUser() throws Exception {
		User user = new User();
		user.setFirstName("Unit1");
		user.setLastName("Test1");
		user.setUsername("UnitTest1");
		user.setEmail("unittest1@dev.in");
		user.setPassword("Abc@123!");
		user.setConfPassword("Abc@123!");
		
		ResponseEntity<User> response = controller.createUser(user);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
	}

}
