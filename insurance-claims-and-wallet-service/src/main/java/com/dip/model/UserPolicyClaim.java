package com.dip.model;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
