package com.blocadminmicro.webservice.dto;

import com.blocadminmicro.webservice.utils.BudgetType;

import lombok.Data;

@Data
public class BudgetDTO {

	private Long id;
	private short budgetType;
	private BudgetType budgetTypeEnum;
	private double totalSum;
	private double leftoverSum;
	private String details;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(short budgetType) {
		this.budgetType = budgetType;
		setBudgetTypeEnum(BudgetType.getNameByCode(budgetType));
	}

	public BudgetType getBudgetTypeEnum() {
		return budgetTypeEnum;
	}

	public void setBudgetTypeEnum(BudgetType budgetTypeEnum) {
		this.budgetTypeEnum = budgetTypeEnum;
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	public double getLeftoverSum() {
		return leftoverSum;
	}

	public void setLeftoverSum(double leftoverSum) {
		this.leftoverSum = leftoverSum;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Budget{" + "id=" + id + ", type=" + budgetType + ", totalSum=" + totalSum + ", leftoverSum="
				+ leftoverSum + ", details=" + details + '}';
	}
}
