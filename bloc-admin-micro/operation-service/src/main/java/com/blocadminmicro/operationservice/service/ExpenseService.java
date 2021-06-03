package com.blocadminmicro.operationservice.service;

import java.util.List;

import com.blocadminmicro.operationservice.dto.ExpenseDTO;


public interface ExpenseService {
	
	public abstract List<ExpenseDTO> getExpenses();

	public abstract void saveExpense(ExpenseDTO expenseDTO);

	public abstract void deleteExpense(Long id);

	public abstract ExpenseDTO getExpense(Long id);
}
