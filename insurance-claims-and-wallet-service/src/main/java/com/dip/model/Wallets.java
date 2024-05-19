package com.dip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Wallets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long walletId;
	private String username;
	private int walletBalance;
	private String walletType;
	private int addedBalance;	

}
