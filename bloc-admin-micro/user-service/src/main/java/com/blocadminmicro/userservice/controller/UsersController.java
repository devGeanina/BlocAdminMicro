package com.blocadminmicro.userservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.userservice.dto.UserDTO;
import com.blocadminmicro.userservice.service.UserService;

@RestController("/users")
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping 
	public List<UserDTO> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{id}")
	UserDTO findUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PostMapping("/users/save")
	public List<UserDTO> saveOrUpdateUser(@RequestBody UserDTO userDTO) {
		userService.saveUser(userDTO);
		List<UserDTO> users = userService.getUsers();
		return users;
	}

	@GetMapping("/users/delete/{id}")
	public List<UserDTO> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return userService.getUsers();
	}
}