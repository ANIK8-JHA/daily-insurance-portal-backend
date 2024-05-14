package com.dip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table
@Data
public class Wallets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long walletId;
	private String username;
	private int walletBalance;
	@NotBlank(message = "Mode of payment not selected")
	private String walletType;
	@NotBlank(message = "Balance value is 0")
	private int addedBalance;
	public Wallets() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wallets(long walletId, String username, int walletBalance, String walletType, int addedBalance) {
		super();
		this.walletId = walletId;
		this.username = username;
		this.walletBalance = walletBalance;
		this.walletType = walletType;
		this.addedBalance = addedBalance;
	}
	public long getWalletId() {
		return walletId;
	}
	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
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
	public int getAddedBalance() {
		return addedBalance;
	}
	public void setAddedBalance(int addedBalance) {
		this.addedBalance = addedBalance;
	}
	@Override
	public String toString() {
		return "Wallets [walletId=" + walletId + ", username=" + username + ", walletBalance=" + walletBalance
				+ ", walletType=" + walletType + ", addedBalance=" + addedBalance + "]";
	}
	
	

}
