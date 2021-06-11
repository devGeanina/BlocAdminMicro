package com.blocadminmicro.operationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.blocadminmicro.operationservice.entity.Household;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long>{

	@Query("SELECT DISTINCT h FROM Household h LEFT JOIN h.expenses expense where expense.leftoverSum > 0 and expense.payedInFull = false")
	List<Household> getHouseholdsWithDebt();
}
