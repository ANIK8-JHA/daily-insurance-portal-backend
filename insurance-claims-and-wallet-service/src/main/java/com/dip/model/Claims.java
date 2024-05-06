package com.dip.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Claims {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long claimsId;
	private String username;
	private String policyName;
	private Date dateOfPurchase;
	private String claimAmount;

	public Claims() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Claims(long claimsId, String username, String policyName, Date dateOfPurchase, String claimAmount) {
		super();
		this.claimsId = claimsId;
		this.username = username;
		this.policyName = policyName;
		this.dateOfPurchase = dateOfPurchase;
		this.claimAmount = claimAmount;
	}

	public long getClaimsId() {
		return claimsId;
	}

	public void setClaimsId(long claimsId) {
		this.claimsId = claimsId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public String getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}

	@Override
	public String toString() {
		return "Claims [claimsId=" + claimsId + ", username=" + username + ", policyName=" + policyName
				+ ", dateOfPurchase=" + dateOfPurchase + ", claimAmount=" + claimAmount + "]";
	}

}
