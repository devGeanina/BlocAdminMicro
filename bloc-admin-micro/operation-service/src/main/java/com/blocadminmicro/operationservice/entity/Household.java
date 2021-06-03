package com.blocadminmicro.operationservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "households")
public class Household implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "building_nr", nullable = false)
	private int buildingNr;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@Column(name = "appartment_nr", nullable = false)
	private int appartmentNr;

	@Column(name = "details", nullable = true)
	private String details;

	@Column(name = "rooms_nr", nullable = false)
	private int roomsNr;

	@Column(name = "nr_curr_occupants", nullable = false)
	private int nrCurrentOccupants;

	@Column(name = "total_capacity", nullable = false)
	private int totalCapacity;

	@ManyToMany(mappedBy = "householdExpenses", fetch = FetchType.LAZY)
	private List<Expense> expenses;

	public Household() {
	}

	public Household(int buildingNr, String ownerName, int appartmentNr, String details, int roomsNr,
			int nrCurrentOccupants, int totalCapacity) {
		this.buildingNr = buildingNr;
		this.ownerName = ownerName;
		this.appartmentNr = appartmentNr;
		this.details = details;
		this.roomsNr = roomsNr;
		this.nrCurrentOccupants = nrCurrentOccupants;
		this.totalCapacity = totalCapacity;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getRoomsNr() {
		return roomsNr;
	}

	public void setRoomsNr(int roomsNr) {
		this.roomsNr = roomsNr;
	}

	public int getNrCurrentOccupants() {
		return nrCurrentOccupants;
	}

	public void setNrCurrentOccupants(int nrCurrentOccupants) {
		this.nrCurrentOccupants = nrCurrentOccupants;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return "Household [id=" + id + ", buildingNr=" + buildingNr + ", ownerName=" + ownerName + ", appartmentNr="
				+ appartmentNr + ", details=" + details + ", roomsNr=" + roomsNr + ", nrCurrentOccupants="
				+ nrCurrentOccupants + ", totalCapacity=" + totalCapacity + ", expenses=" + expenses + "]";
	}
}
