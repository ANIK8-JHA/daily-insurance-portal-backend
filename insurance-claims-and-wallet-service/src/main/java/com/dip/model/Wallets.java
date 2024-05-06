package com.dip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Wallets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long walledId;
	private String username;
	private int walletBalance;
	private String walletType;
	public Wallets() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wallets(long walledId, String username, int walletBalance, String walletType) {
		super();
		this.walledId = walledId;
		this.username = username;
		this.walletBalance = walletBalance;
		this.walletType = walletType;
	}
	public long getWalledId() {
		return walledId;
	}
	public void setWalledId(long walledId) {
		this.walledId = walledId;
	}
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public int getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(int walletBalance) {
		this.walletBalance = walletBalance;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	@Override
	public String toString() {
		return "Wallets [walledId=" + walledId + ", username=" + username + ", walletBalance=" + walletBalance
				+ ", WalletType=" + walletType + "]";
	}
	
	

}
