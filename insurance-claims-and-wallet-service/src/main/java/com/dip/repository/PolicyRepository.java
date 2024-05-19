package com.dip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dip.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
	
	public Policy findByPolicyName(String policyName);

	@Query(value = "select policy_name from policy", nativeQuery = true)
	public List<String> findAllPolicyName();
}
