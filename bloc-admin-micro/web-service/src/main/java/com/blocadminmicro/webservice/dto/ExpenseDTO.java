package com.blocadminmicro.webservice.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.blocadminmicro.webservice.utils.ExpenseType;
import com.blocadminmicro.webservice.utils.Utils;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class ExpenseDTO {

	private Long id;
	private short expenseType;
	private ExpenseType expenseTypeEnum;
	private double totalSum;
	private double leftoverSum;
	private boolean payedInFull;
	private String details;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private List<Long> householdIds;
	private List<String> householdsAddresses;
	private String formattedDueDate;
	private String expenseAddressesFormatted;
	
	public ExpenseDTO(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isPayedInFull() {
		return payedInFull;
	}

	public void setPayedInFull(boolean payedInFull) {
		this.payedInFull = payedInFull;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<Long> getHouseholdIds() {
		return householdIds;
	}

	public void setHouseholdIds(List<Long> householdIds) {
		this.householdIds = householdIds;
	}

	public List<String> getHouseholdsAddresses() {
		return householdsAddresses;
	}

	public void setHouseholdsAddresses(List<String> householdsAddresses) {
		this.householdsAddresses = householdsAddresses;
	}

	public String getFormattedDueDate() {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
		return formattedDueDate;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
		setExpenseTypeEnum(ExpenseType.getNameByCode(expenseType));
	}

	public ExpenseType getExpenseTypeEnum() {
		return expenseTypeEnum;
	}

	public void setExpenseTypeEnum(ExpenseType expenseTypeEnum) {
		this.expenseTypeEnum = expenseTypeEnum;
	}

	public String getExpenseAddressesFormatted() {
		if (householdsAddresses != null && !householdsAddresses.isEmpty())
			this.expenseAddressesFormatted = householdsAddresses.stream().map(String::valueOf)
					.collect(Collectors.joining("/"));
		else
			this.expenseAddressesFormatted = "-";
		return expenseAddressesFormatted;
	}

	@Override
	public String toString() {
		return "ExpenseDTO [id=" + id + ", expenseType=" + expenseType + ", totalSum=" + totalSum + ", leftoverSum="
				+ leftoverSum + ", payedInFull=" + payedInFull + ", details=" + details + ", dueDate=" + dueDate
				+ ", householdIds=" + householdIds + ", householdsAddresses=" + householdsAddresses + "]";
	}
}
