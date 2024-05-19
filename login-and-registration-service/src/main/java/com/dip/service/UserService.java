package com.dip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dip.exceptions.UserAlreadyExistsException;
import com.dip.exceptions.UserNotFoundException;
import com.dip.model.User;
import com.dip.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public List<User> getUser() {
		return this.repo.findAll();
	}
	
	public User createUser(User user) throws UserAlreadyExistsException {
//		log.error("No user found", repo.findByUsername(user.getUsername()).orElse(null));
		User oldUser = repo.findByUsername(user.getUsername()).orElse(null);
		if(oldUser != null) {
			throw new UserAlreadyExistsException("User already presnet with the username : " + user.getUsername());
		}
		User oldUser2 = repo.findByEmail(user.getEmail()).orElse(null);
		if(oldUser2 != null) throw new UserAlreadyExistsException("User already present with the email : " + user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfPassword(passwordEncoder.encode(user.getConfPassword()));
		return this.repo.save(user);
	}
	
	public long getIdByUserName(String username) throws UserNotFoundException {
		if(repo.findByUsername(username) == null) throw new UserNotFoundException("User not found with username : " + username);
		User user = repo.findByUsername(username).orElseThrow();
		return user.getId();
	}

}