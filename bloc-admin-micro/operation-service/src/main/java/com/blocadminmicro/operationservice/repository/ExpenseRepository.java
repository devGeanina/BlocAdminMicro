package com.blocadminmicro.operationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocadminmicro.operationservice.entity.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
