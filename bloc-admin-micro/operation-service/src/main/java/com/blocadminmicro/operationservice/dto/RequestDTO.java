package com.blocadminmicro.operationservice.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Builder
public class RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private short requestType;
	private String name;
	private String householdAddress;
	private Long householdId;
	private String details;
	private boolean resolved;
	private Date dueDate;
	
	public RequestDTO(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getHouseholdAddress() {
		if (householdAddress != null)
			return householdAddress;
		else
			return "-";
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public Long getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(Long householdId) {
		this.householdId = householdId;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((householdAddress == null) ? 0 : householdAddress.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + requestType;
		result = prime * result + (resolved ? 1231 : 1237);
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
		RequestDTO other = (RequestDTO) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (householdAddress == null) {
			if (other.householdAddress != null)
				return false;
		} else if (!householdAddress.equals(other.householdAddress))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (requestType != other.requestType)
			return false;
		if (resolved != other.resolved)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestDTO [id=" + id + ", requestType=" + requestType + ", name=" + name + ", householdAddress="
				+ householdAddress + ", details=" + details + ", resolved=" + resolved + ", dueDate=" + dueDate + "]";
	}
}
