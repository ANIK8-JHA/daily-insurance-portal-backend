package com.dip.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dip.model.Insurances;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurances, Long> {
	
	@Query(value = "select puchase_data from insurances where username=?1 and insurance_name=?2", nativeQuery = true)
	public Date findByUsernameAndInsuranceName(String username, String insuranceName);
	
	public List<Insurances> findAllByUsername(String username);
}
