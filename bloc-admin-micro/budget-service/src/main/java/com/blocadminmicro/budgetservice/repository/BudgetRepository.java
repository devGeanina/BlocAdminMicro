package com.blocadminmicro.budgetservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocadminmicro.budgetservice.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long>{

}
