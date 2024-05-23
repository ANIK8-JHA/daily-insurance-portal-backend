package com.dip.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.dip.model.JwtRequest;
import com.dip.model.User;
import com.dip.service.UserService;
import com.dip.util.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private UserDetailsService userDetailsService;
 
    @MockBean
    private AuthenticationManager authenticationManager;
 
    @MockBean
    private JwtHelper jwtHelper;
 
    @MockBean
    private UserService userService;
 
    @Test
    public void testLoginSuccess() throws Exception {
        // Mock the UserDetails and token generation
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtHelper.generateToken(userDetails)).thenReturn("dummyToken");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userService.getIdByUserName(anyString())).thenReturn(1L);
 
        JwtRequest request = new JwtRequest("testUser", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
 
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("dummyToken")))
                .andExpect(jsonPath("$.username", is("testUser")))
                .andExpect(jsonPath("$.userId", is(1)));
    }
 
    @Test
    public void testLoginFailure() throws Exception {
        // Simulate authentication failure
        doThrow(new BadCredentialsException("Invalid Credentials")).when(authenticationManager).authenticate(any());
 
        JwtRequest request = new JwtRequest("invalidUser", "wrongPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
 
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }
 
    @Test
    public void testCreateUserSuccess() throws Exception {
        // Mock the user creation
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("Abc@123!");
        user.setFirstName("test");
        user.setLastName("user");
        user.setEmail("testuser123@dev.in");
        user.setConfPassword("Abc@123!");
        User savedUser = new User();
        savedUser.setUsername("testUser");
        savedUser.setPassword("Abc@123!");
        savedUser.setFirstName("test");
        savedUser.setLastName("user");
        savedUser.setEmail("testuser123@dev.in");
        savedUser.setConfPassword("Abc@123!");
        when(userService.createUser(any(User.class))).thenReturn(savedUser);
 
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(user);
 
        mockMvc.perform(post("/auth/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("testUser")));
    }
}