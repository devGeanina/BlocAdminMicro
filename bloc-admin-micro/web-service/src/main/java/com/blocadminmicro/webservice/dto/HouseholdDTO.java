package com.blocadminmicro.webservice.dto;

import lombok.Data;

@Data
public class HouseholdDTO {

	private Long id;
	private int buildingNr;
	private int appartmentNr;
	private String details;
	private int roomsNr;
	private int nrCurrentOccupants;
	private int totalCapacity;
	private String ownerName;
	private double totalDebt;
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public double getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}

	public String getAddress() {
		address = "B. ".concat(String.valueOf(buildingNr)).concat(", Ap.").concat(String.valueOf(appartmentNr));
		return address;
	}

	@Override
	public String toString() {
		return "HouseholdDTO [id=" + id + ", buildingNr=" + buildingNr + ", appartmentNr=" + appartmentNr + ", details="
				+ details + ", roomsNr=" + roomsNr + ", nrCurrentOccupants=" + nrCurrentOccupants + ", totalCapacity="
				+ totalCapacity + ", ownerName=" + ownerName + ", totalDebt=" + totalDebt + ", address=" + address
				+ "]";
	}
}
