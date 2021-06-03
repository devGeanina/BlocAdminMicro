package com.blocadminmicro.operationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocadminmicro.operationservice.dto.ExpenseDTO;
import com.blocadminmicro.operationservice.entity.Expense;
import com.blocadminmicro.operationservice.entity.Household;
import com.blocadminmicro.operationservice.repository.ExpenseRepository;
import com.blocadminmicro.operationservice.repository.HouseholdRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseDAO;

	private final HouseholdRepository householdDAO;

	@Autowired
	public ExpenseServiceImpl(ExpenseRepository expenseDAO, HouseholdRepository householdDAO) {
		super();
		this.expenseDAO = expenseDAO;
		this.householdDAO = householdDAO;
	}

	@Transactional
	@Override
	public List<ExpenseDTO> getExpenses() {
		List<ExpenseDTO> dtos = new ArrayList<>();

		List<Expense> entities = expenseDAO.findAll();
		for (Expense entity : entities) {
			ExpenseDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private ExpenseDTO getDTO(Expense entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}

		ExpenseDTO entityDTO = new ExpenseDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setExpenseType(entity.getExpenseType());
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setPayedInFull(entity.isPayedInFull());

		List<Long> householdIds = new ArrayList<Long>();
		List<String> householdsAddresses = new ArrayList<String>();

		if (entity.getHouseholdExpenses() != null && !entity.getHouseholdExpenses().isEmpty()) {
			for (Household household : entity.getHouseholdExpenses()) {
				householdIds.add(household.getId());
				String address = "B.".concat(String.valueOf(household.getBuildingNr()).concat(", Ap.")
						.concat(String.valueOf(household.getAppartmentNr())));
				householdsAddresses.add(address);
			}
		}

		entityDTO.setHouseholdIds(householdIds);
		entityDTO.setHouseholdsAddresses(householdsAddresses);
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveExpense(ExpenseDTO expenseDTO) {
		if (expenseDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Expense expense = new Expense();
		expense = getEntity(expenseDTO);
		expenseDAO.save(expense);
	}

	private Expense getEntity(ExpenseDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Expense entity = new Expense();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setExpenseType(entityDTO.getExpenseType());
		entity.setDueDate(entityDTO.getDueDate());
		entity.setPayedInFull(entityDTO.isPayedInFull());

		if (entityDTO.isPayedInFull())
			entity.setLeftoverSum(0.0);

		List<Household> households = new ArrayList<Household>();
		if (entityDTO.getHouseholdIds() != null && entityDTO.getHouseholdIds().isEmpty() == false) {
			for (Long houseId : entityDTO.getHouseholdIds()) {
				Optional<Household> household = householdDAO.findById(houseId);
				households.add(household.get());
			}
		}

		entity.setHouseholdExpenses(households);

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteExpense(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		expenseDAO.deleteById(id);
	}

	@Override
	public ExpenseDTO getExpense(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Expense> entity = expenseDAO.findById(id);
		ExpenseDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
