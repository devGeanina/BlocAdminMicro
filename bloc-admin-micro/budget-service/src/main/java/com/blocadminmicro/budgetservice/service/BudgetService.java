package com.blocadminmicro.budgetservice.service;

import java.util.List;

import com.blocadminmicro.budgetservice.dto.BudgetDTO;

public interface BudgetService {

	public abstract List<BudgetDTO> getBudgets();

	public abstract void saveBudget(BudgetDTO budgetDTO);

	public abstract void deleteBudget(Long budgetId);

	public abstract BudgetDTO getBudget(Long budgetId);
}
