package com.dip.classes;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Claim {
	
	private String policyName;
	private LocalDateTime purchaseDttm;
	private int claimAmount;
}
