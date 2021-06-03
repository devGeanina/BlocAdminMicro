package com.blocadminmicro.operationservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "expenses")
public class Expense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "expense_type", nullable = false)
	private short expenseType;

	@Column(name = "total_sum", nullable = false)
	private double totalSum;

	@Column(name = "leftover_sum", nullable = false)
	private double leftoverSum;

	@Column(name = "payed_in_full", nullable = false)
	private boolean payedInFull;

	@Column(name = "details", nullable = true)
	private String details;

	@Column(name = "due_date", nullable = false)
	private Date dueDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "expenses_households", joinColumns = {
			@JoinColumn(name = "expense_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "household_id", referencedColumnName = "id") })
	private List<Household> householdExpenses;

	public Expense() {
	}

	public Expense(int appartmentNr, short expenseType, double totalSum, double leftoverSum, boolean payedInFull,
			String details, Date dueDate) {
		super();
		this.expenseType = expenseType;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.payedInFull = payedInFull;
		this.details = details;
		this.dueDate = dueDate;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Household> getHouseholdExpenses() {
		return householdExpenses;
	}

	public void setHouseholdExpenses(List<Household> householdExpenses) {
		this.householdExpenses = householdExpenses;
	}

	@Override
	public String toString() {
		return "Expense [expenseType=" + expenseType + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum
				+ ", payedInFull=" + payedInFull + ", details=" + details + ", dueDate=" + dueDate + "]";
	}
}
