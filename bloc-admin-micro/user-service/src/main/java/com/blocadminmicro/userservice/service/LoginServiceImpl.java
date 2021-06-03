package com.blocadminmicro.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicro.userservice.dto.UserDTO;
import com.blocadminmicro.userservice.entity.User;
import com.blocadminmicro.userservice.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	private final UserRepository loginDAO;

	@Autowired
	public LoginServiceImpl(UserRepository loginDAO) {
		this.loginDAO = loginDAO;
	}

	@Transactional
	@Override
	public UserDTO login(String user, String password) {
		User userDB = loginDAO.login(user, password);
		if (userDB != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(userDB.getUsername());
			userDTO.setPassword(userDB.getPassword());
			userDTO.setUserType(userDB.getUserType());
			return userDTO;
		} else {
			return null;
		}
	}

	@Override
	public void logout(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot logout because the user id is null.");
		}
	}
}
