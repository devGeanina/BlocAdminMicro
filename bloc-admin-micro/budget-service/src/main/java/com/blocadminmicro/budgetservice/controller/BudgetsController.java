package com.blocadminmicro.budgetservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.budgetservice.dto.BudgetDTO;
import com.blocadminmicro.budgetservice.service.BudgetService;

@RestController("/budgets")
public class BudgetsController {

	private final BudgetService budgetService;
	
	public BudgetsController(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@GetMapping 
	public List<BudgetDTO> getBudgets() {
		return budgetService.getBudgets();
	}

	@GetMapping("/budgets/{id}")
	BudgetDTO findUser(@PathVariable Long id) {
		return budgetService.getBudget(id);
	}

	@PostMapping("/budgets/save")
	public List<BudgetDTO> saveOrUpdateBudget(@RequestBody BudgetDTO budgetDTO) {
		budgetService.saveBudget(budgetDTO);
		return budgetService.getBudgets();
	}

	@GetMapping("/budgets/delete/{id}")
	public List<BudgetDTO> deleteBudget(@PathVariable Long id) {
		budgetService.deleteBudget(id);
		return budgetService.getBudgets();
	}
}