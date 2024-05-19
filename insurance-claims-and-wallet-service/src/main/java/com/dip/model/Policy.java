package com.dip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Policy {
	
	@Id
	private long policyId;
	private String policyName;
	private int policyPremium;
	private int policyCoverage;
	

}
