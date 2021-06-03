package com.blocadminmicro.userservice.service;

import java.util.List;

import com.blocadminmicro.userservice.dto.UserDTO;

public interface UserService {

	public abstract List<UserDTO> getUsers();

	public abstract void saveUser(UserDTO userDTO);

	public abstract boolean deleteUser(Long userId);

	public abstract UserDTO getUser(Long userId);
}
