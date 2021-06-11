package com.blocadminmicro.webservice.dto;

import com.blocadminmicro.webservice.utils.UserType;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String fullName;
	private String password;
	private int buildingNr;
	private String details;
	private int appartmentNr;
	private String username;
	private UserType userTypeEnum;
	private short userType;

	public UserDTO() {
	}

	public UserDTO(Long id, String firstName, String lastName, String password, int buildingNr, String details,
			int appartmentNr, String username, short userType) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
		this.username = username;
		this.userType = userType;
	}

	public UserDTO(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFullName() {
		return getFirstName().concat(" ").concat(getLastName());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getUserTypeEnum() {
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserType userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
		setUserTypeEnum(UserType.getNameByCode(userType));
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName=" + fullName
				+ ", password=" + password + ", buildingNr=" + buildingNr + ", details=" + details + ", appartmentNr="
				+ appartmentNr + ", username=" + username + ", userTypeEnum=" + userTypeEnum + ", userType=" + userType
				+ "]";
	}
}
