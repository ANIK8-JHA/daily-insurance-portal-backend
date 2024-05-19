package com.dip.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dip.model.UserPolicyClaim;

@Repository
public interface UserPolicyClaimRepository extends JpaRepository<UserPolicyClaim, Long>{
	
	public List<UserPolicyClaim> findByUserId(Long userId);
	
	@Query(value = "select * from user_policy_claim where policy_id=?1 and purchase_dttm=?2", nativeQuery = true)
	public UserPolicyClaim findByPolicyIdAndDop(long policyId, String purchaseDttm);

}
