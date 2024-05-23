package com.dip.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.dip.model.User;
import com.dip.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
	
	@Mock
    private UserRepo repo;
 
    @InjectMocks
    private CustomUserDetailsService userService;
	
	@Test
	public void testLoadUserByUsername() {
		User user = new User();
        user.setFirstName("test");
        user.setLastName("user");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("Abc@123!");
        user.setConfPassword("Abc@123!");
        
        when(repo.findByUsername(anyString())).thenReturn(Optional.of(user));
        
        UserDetails foundUser = userService.loadUserByUsername(user.getUsername());
		
		assertNotNull(foundUser);
		assertEquals(user.getUsername(), foundUser.getUsername());
		
	}

}
