package com.blocadminmicro.userservice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.userservice.dto.LoginDTO;
import com.blocadminmicro.userservice.dto.UserDTO;
import com.blocadminmicro.userservice.service.LoginService;

@RestController("/")
public class LoginController {
	
	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public UserDTO login(@RequestBody LoginDTO loginDTO) {
		UserDTO userDTO = loginService.login(loginDTO.getUsername(), loginDTO.getPassword());
		return userDTO;
	}
}
