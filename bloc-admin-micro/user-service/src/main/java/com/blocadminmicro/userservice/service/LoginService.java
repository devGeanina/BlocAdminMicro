package com.blocadminmicro.userservice.service;

import com.blocadminmicro.userservice.dto.UserDTO;

public interface LoginService {

	public abstract UserDTO login(String user, String password);

	public abstract void logout(Long userId);
}
