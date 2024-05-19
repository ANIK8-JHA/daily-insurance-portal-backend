package com.dip.classes;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Claim {
	
	private String policyName;
	private LocalDateTime purchaseDttm;
	private int claimAmount;
}
