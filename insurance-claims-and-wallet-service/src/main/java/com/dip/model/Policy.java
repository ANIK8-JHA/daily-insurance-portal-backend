package com.dip.model;

import jakarta.persistence.Entity;
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
public class Policy {
	
	@Id
	private long policyId;
	private String policyName;
	private int policyPremium;
	private int policyCoverage;
	

}
