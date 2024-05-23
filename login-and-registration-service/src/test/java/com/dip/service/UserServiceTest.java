package com.dip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dip.exceptions.UserAlreadyExistsException;
import com.dip.exceptions.UserNotFoundException;
import com.dip.model.User;
import com.dip.repository.UserRepo;
 
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
 
    @Mock
    private UserRepo repo;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @InjectMocks
    private UserService userService;
 
    @Test
    public void testGetUser() {
        User user = new User();
        user.setUsername("testUser");
        when(repo.findAll()).thenReturn(Collections.singletonList(user));
 
        List<User> users = userService.getUser();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
    }
 
    @Test
    public void testCreateUserSuccess() throws UserAlreadyExistsException {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("user");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("Abc@123!");
        user.setConfPassword("Abc@123!");
 
        when(repo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(repo.save(any(User.class))).thenReturn(user);
 
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getConfPassword(), createdUser.getConfPassword());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
        
    }
 
    @Test
    public void testCreateUserThrowsExceptionWhenUserExists() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("user");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("Abc@123!");
        user.setConfPassword("Abc@123!");
 
        when(repo.findByUsername(anyString())).thenReturn(Optional.of(user));
 
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });
    }
 
    @Test
    public void testGetIdByUserNameSuccess() throws UserNotFoundException {
        User user = new User();
        user.setId(1L);
        user.setFirstName("test");
        user.setLastName("user");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("Abc@123!");
        user.setConfPassword("Abc@123!");
 
        when(repo.findByUsername(anyString())).thenReturn(Optional.of(user));
 
        long userId = userService.getIdByUserName("testUser");
        assertEquals(1L, userId);
    }
 
    @Test
    public void testGetIdByUserNameThrowsExceptionWhenUserNotFound() {
        when(repo.findByUsername(anyString())).thenReturn(Optional.empty());
 
        assertThrows(NoSuchElementException.class, () -> {
            userService.getIdByUserName("unknownUser");
        });
    }
}