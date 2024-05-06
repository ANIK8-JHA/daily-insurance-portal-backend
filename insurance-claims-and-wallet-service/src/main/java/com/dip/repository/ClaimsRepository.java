package com.dip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dip.model.Claims;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Long> {

	public List<Claims> findAllByUsername(String username);
}
