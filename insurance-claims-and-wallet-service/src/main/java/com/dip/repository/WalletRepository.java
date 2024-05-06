package com.dip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dip.model.Wallets;

@Repository
public interface WalletRepository extends JpaRepository<Wallets, Long> {
	
	public Wallets findByUsername(String username);

}
