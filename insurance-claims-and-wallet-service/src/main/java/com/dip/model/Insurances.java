package com.dip.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Insurances {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long insuranceId;
	private String username;
	private String insuranceName;
	private String premium;
	private Date puchaseDate;
	public Insurances() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Insurances(long insuranceId, String username, String insuranceName, String premium, Date puchaseDate) {
		super();
		this.insuranceId = insuranceId;
		this.username = username;
		this.insuranceName = insuranceName;
		this.premium = premium;
		this.puchaseDate = puchaseDate;
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
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public Date getPuchaseDate() {
		return puchaseDate;
	}
	public void setPuchaseDate(Date puchaseDate) {
		this.puchaseDate = puchaseDate;
	}
	@Override
	public String toString() {
		return "Insurances [insuranceId=" + insuranceId + ", username=" + username + ", insuranceName=" + insuranceName
				+ ", premium=" + premium + ", puchaseDate=" + puchaseDate + "]";
	}
	
	
}
