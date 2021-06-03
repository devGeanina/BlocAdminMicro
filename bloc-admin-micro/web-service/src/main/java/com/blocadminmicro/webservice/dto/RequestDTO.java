package com.blocadminmicro.webservice.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.blocadminmicro.webservice.utils.HouseholdRequestType;
import com.blocadminmicro.webservice.utils.Utils;

import lombok.Data;

@Data
public class RequestDTO {

	private Long id;
	private short requestType;
	private HouseholdRequestType requestTypeEnum;
	private String name;
	private String details;
	private boolean resolved;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private String formattedDueDate;
	private String householdAddress;
	private Long householdId;

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
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public String getFormattedDueDate() {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
		return formattedDueDate;
	}

	public void setFormattedDueDate(String formattedDueDate) {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
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
		setRequestTypeEnum(HouseholdRequestType.getNameByCode(requestType));
	}

	public HouseholdRequestType getRequestTypeEnum() {
		return requestTypeEnum;
	}

	public void setRequestTypeEnum(HouseholdRequestType requestTypeEnum) {
		this.requestTypeEnum = requestTypeEnum;
	}

	public Long getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(Long householdId) {
		this.householdId = householdId;
	}

	@Override
	public String toString() {
		return "RequestDTO [id=" + id + ", requestType=" + requestType + ", requestTypeEnum=" + requestTypeEnum
				+ ", name=" + name + ", details=" + details + ", resolved=" + resolved + ", dueDate=" + dueDate
				+ ", formattedDueDate=" + formattedDueDate + ", householdAddress=" + householdAddress + ", householdId="
				+ householdId + "]";
	}
}
