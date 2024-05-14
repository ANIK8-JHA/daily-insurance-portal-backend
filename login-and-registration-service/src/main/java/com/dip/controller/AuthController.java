package com.dip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dip.exceptions.UserAlreadyExistsException;
import com.dip.exceptions.UserNotFoundException;
import com.dip.model.JwtRequest;
import com.dip.model.JwtResponse;
import com.dip.model.User;
import com.dip.service.UserService;
import com.dip.util.JwtHelper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws UserNotFoundException {
		this.doAuthenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		
		log.info(token);
		
		JwtResponse response = new JwtResponse(token, userDetails.getUsername(), userService.getIdByUserName(request.getUsername()));
		
		log.info(response.toString());
		
		return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
	}
	
	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authentication);
			log.info("Authentication Successful");
		}catch(BadCredentialsException e) {
			log.error("Authentication Unsuccessful");
			throw new BadCredentialsException("Invalid Credentials");
		}
	}

	
	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws Exception {
		log.info("Creating User");
		log.info(user.toString());
		User newUser =  this.userService.createUser(user);
		log.info("User created successfully");
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
}
