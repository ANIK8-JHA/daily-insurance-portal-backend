package com.dip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserPolicyClaim {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long policyClaimId;
	
	private long userId;
	private long policyId;
	private String purchaseDttm;
	private int claimAmount;
	private String claimStatus;
	private String claimDttm;

}
