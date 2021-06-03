package com.blocadminmicro.operationservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.operationservice.dto.ExpenseDTO;
import com.blocadminmicro.operationservice.service.ExpenseService;


@RestController
public class ExpensesController {

	private final ExpenseService expenseService;

	public ExpensesController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@GetMapping("/expenses") 
	public List<ExpenseDTO> getExpenses() {
		return expenseService.getExpenses();
	}

	@GetMapping("/expenses/{id}")
	ExpenseDTO findExpense(@PathVariable Long id) {
		return expenseService.getExpense(id);
	}

	@PostMapping("/expenses/save")
	public List<ExpenseDTO> saveOrUpdateBudget(@RequestBody ExpenseDTO expenseDTO) {
		expenseService.saveExpense(expenseDTO);
		return expenseService.getExpenses();
	}

	@GetMapping("/expenses/delete/{id}")
	public List<ExpenseDTO> deleteExpense(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return expenseService.getExpenses();
	}
}