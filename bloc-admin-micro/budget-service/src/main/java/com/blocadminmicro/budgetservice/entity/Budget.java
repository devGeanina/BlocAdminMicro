package com.blocadminmicro.budgetservice.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "budgets")
public class Budget implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "budget_type", nullable = false)
    private short type;
	
	@Column(name = "total_sum", nullable = false)
    private double totalSum;
	
	@Column(name = "leftover_sum", nullable = false)
    private double leftoverSum;
	
	@Column(name = "details", nullable = true)
    private String details;

	public Budget() {}
	
	public Budget(short type, double totalSum, double leftoverSum, String details) {
		this.type = type;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.details = details;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Budget [type=" + type + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum + ", details="
				+ details + "]";
	}
}
