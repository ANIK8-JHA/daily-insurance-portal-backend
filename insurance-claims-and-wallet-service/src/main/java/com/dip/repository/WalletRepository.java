package com.dip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dip.model.Wallets;

@Repository
public interface WalletRepository extends JpaRepository<Wallets, Long> {
	
	@Query(value = "select wallet_balance from daily_insurance_portal.wallets where username=?1 order by wallet_id desc limit 1", nativeQuery = true)
	public int findWalletBalanceByUsername(String username);
	
	public List<Wallets> findByUsername(String username);
	
	@Query(value = "select * from daily_insurance_portal.wallets where username=?1 order by wallet_id desc limit 1", nativeQuery = true)
	public Wallets findLatesTransaction(String username);

}
