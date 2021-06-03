package com.blocadminmicro.userservice.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "username", nullable = false, unique = true)
    private String username;
	
	@Column(name = "password", nullable = true)
    private String password;

	@Column(name = "first_name", nullable = false)
    private String firstName;
	
	@Column(name = "last_name", nullable = false)
    private String lastName;
	
	@Column(name = "user_type", nullable = false)
    private short userType;
	
	@Column(name = "building_nr", nullable = false)
    private int buildingNr;
	
	@Column(name = "details", nullable = true)
    private String details;
	
	@Column(name = "appartment_nr", nullable = false)
    private int appartmentNr;
	
	public User() {}

	public User(String username, String password, String firstName, String lastName, short userType, int buildingNr,
			String details, int appartmentNr) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", userType=" + userType + ", buildingNr=" + buildingNr + ", details=" + details
				+ ", appartmentNr=" + appartmentNr + "]";
	}
}
