package com.dip.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table
public class Insurances {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long insuranceId;
	private String username;
	@NotBlank(message = "Policy name not selected")
	private String insuranceName;
	@NotBlank(message = "Premium not selected")
	private int premium;
	private Date purchaseDate;
	public Insurances() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Insurances(long insuranceId, String username, String insuranceName, int premium, Date purchaseDate) {
		super();
		this.insuranceId = insuranceId;
		this.username = username;
		this.insuranceName = insuranceName;
		this.premium = premium;
		this.purchaseDate = purchaseDate;
	}
	public long getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(long insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public int getPremium() {
		return premium;
	}
	public void setPremium(int premium) {
		this.premium = premium;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "Insurances [insuranceId=" + insuranceId + ", username=" + username + ", insuranceName=" + insuranceName
				+ ", premium=" + premium + ", purchaseDate=" + purchaseDate + "]";
	}
	
	
}
