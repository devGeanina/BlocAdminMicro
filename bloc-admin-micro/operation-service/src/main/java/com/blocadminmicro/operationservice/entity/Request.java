package com.blocadminmicro.operationservice.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "requests")
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "request_type", nullable = false)
	private short requestType;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "household_id", nullable = true)
	private Household household;

	@Column(name = "details", nullable = true)
	private String details;

	@Column(name = "is_resolved", nullable = false)
	private boolean isResolved;

	@Column(name = "due_date", nullable = false)
	private Date dueDate;

	public Request() {
	}

	public Request(short requestType, String name, String details, boolean isResolved, Date dueDate) {
		this.requestType = requestType;
		this.name = name;
		this.details = details;
		this.isResolved = isResolved;
		this.dueDate = dueDate;
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

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}

	public boolean isResolved() {
		return isResolved;
	}

	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
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

	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	@Override
	public String toString() {
		return "Request [requestType=" + requestType + ", name=" + name + ", details=" + details + ", isResolved="
				+ isResolved + ", dueDate=" + dueDate + "]";
	}
}
