package com.blocadminmicro.budgetservice.dto;

import java.io.Serializable;
import lombok.Builder;

@Builder
public class BudgetDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private short budgetType;
    private double totalSum;
    private double leftoverSum;
    private String details;
	
    public BudgetDTO(){}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + budgetType;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(leftoverSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BudgetDTO other = (BudgetDTO) obj;
		if (budgetType != other.budgetType)
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(leftoverSum) != Double.doubleToLongBits(other.leftoverSum))
			return false;
		if (Double.doubleToLongBits(totalSum) != Double.doubleToLongBits(other.totalSum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BudgetDTO [id=" + id + ", budgetType=" + budgetType + ", totalSum=" + totalSum + ", leftoverSum="
				+ leftoverSum + ", details=" + details + "]";
	}
}
