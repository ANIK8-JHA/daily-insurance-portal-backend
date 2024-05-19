package com.dip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dip.exceptions.UserAlreadyExistsException;
import com.dip.exceptions.UserNotFoundException;
import com.dip.model.User;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@Test
	public void testCreateUser() throws UserAlreadyExistsException {
		User user = new User();
		user.setFirstName("Unit");
		user.setLastName("Test");
		user.setUsername("UnitTest");
		user.setEmail("unittest@dev.in");
		user.setPassword("Abc@123!");
		user.setConfPassword("Abc@123!");
		
		User newUser = service.createUser(user);
		
		assertEquals(user.getUsername(), newUser.getUsername());
	}
	
	@Test
	public void testGetIdByUserName() throws UserNotFoundException {
		long requestId = 2L;
		String username = "anik8-jha";
		long responseId = service.getIdByUserName(username);
		
		assertEquals(requestId, responseId);
	}

}
