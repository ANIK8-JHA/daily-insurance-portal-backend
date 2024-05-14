package com.dip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dip.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
}
