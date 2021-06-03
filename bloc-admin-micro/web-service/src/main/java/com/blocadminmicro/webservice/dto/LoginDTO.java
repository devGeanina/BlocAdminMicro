package com.blocadminmicro.webservice.dto;

import lombok.Data;

@Data
public class LoginDTO {

	private Long id;
	private String username;
	private String password;

	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public LoginDTO() {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", password=" + password + "]";
	}
}
