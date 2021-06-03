package com.blocadminmicro.budgetservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicro.budgetservice.dto.BudgetDTO;
import com.blocadminmicro.budgetservice.entity.Budget;
import com.blocadminmicro.budgetservice.repository.BudgetRepository;


@Service
public class BudgetServiceImpl implements BudgetService{
	
	private final BudgetRepository budgetDAO;
	
	@Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetDAO = budgetRepository;
    }

	@Transactional
	@Override
	public List<BudgetDTO> getBudgets() {
		List<BudgetDTO> dtos = new ArrayList<>();

		List<Budget> entities = budgetDAO.findAll();
		for (Budget entity : entities) {
			BudgetDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private BudgetDTO getDTO(Budget entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		BudgetDTO entityDTO = new BudgetDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setBudgetType(entity.getType());
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveBudget(BudgetDTO budgetDTO) {
		if (budgetDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Budget budget = new Budget();
		budget = getEntity(budgetDTO);
		budgetDAO.save(budget);
	}

	private Budget getEntity(BudgetDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Budget entity = new Budget();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());
		
		if(entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setType(entityDTO.getBudgetType());
		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteBudget(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		budgetDAO.deleteById(id);
	}

	@Override
	public BudgetDTO getBudget(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Budget> entity = budgetDAO.findById(id);
		BudgetDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
