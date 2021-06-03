package com.blocadminmicro.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicro.userservice.dto.UserDTO;
import com.blocadminmicro.userservice.entity.User;
import com.blocadminmicro.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userDAO;

	@Autowired
	public UserServiceImpl(UserRepository userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	@Override
	public List<UserDTO> getUsers() {
		List<UserDTO> users = new ArrayList<>();

		List<User> userEntities = userDAO.findAll();
		for (User userEntity : userEntities) {
			UserDTO userDTO = getDTO(userEntity);
			users.add(userDTO);
		}
		return users;
	}

	private UserDTO getDTO(User userEntity) {
		if (userEntity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		UserDTO entityDTO = new UserDTO();
		entityDTO.setAppartmentNr(userEntity.getAppartmentNr());
		entityDTO.setBuildingNr(userEntity.getBuildingNr());
		entityDTO.setDetails(userEntity.getDetails());
		entityDTO.setFirstName(userEntity.getFirstName());
		entityDTO.setLastName(userEntity.getLastName());
		entityDTO.setUsername(userEntity.getUsername());

		if (userEntity.getPassword() != null)
			entityDTO.setPassword(userEntity.getPassword());
		entityDTO.setUserType(userEntity.getUserType());
		entityDTO.setId(userEntity.getId());
		return entityDTO;
	}

	private User getEntity(UserDTO userEntityDTO) {
		if (userEntityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		User userEntity = new User();
		userEntity.setAppartmentNr(userEntityDTO.getAppartmentNr());
		userEntity.setBuildingNr(userEntityDTO.getBuildingNr());

		if (userEntityDTO.getDetails() != null)
			userEntity.setDetails(userEntityDTO.getDetails());

		if (userEntityDTO.getPassword() != null)
			userEntity.setPassword(userEntityDTO.getPassword());

		userEntity.setFirstName(userEntityDTO.getFirstName());
		userEntity.setLastName(userEntityDTO.getLastName());

		if (userEntityDTO.getUsername() != null)
			userEntity.setUsername(userEntityDTO.getUsername());

		userEntity.setUserType(userEntityDTO.getUserType());

		if (userEntityDTO.getId() != null)
			userEntity.setId(userEntityDTO.getId());

		return userEntity;
	}

	@Override
	public void saveUser(UserDTO userDTO) {
		if (userDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		User user = new User();
		user = getEntity(userDTO);
		if (user.getUsername() == null)
			user.setUsername(user.getLastName().concat("_").concat(user.getFirstName()));
		userDAO.save(user);
	}

	@Override
	public boolean deleteUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		userDAO.deleteById(userId);
		boolean exists = userDAO.existsById(userId);
		return exists;
	}

	@Override
	public UserDTO getUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<User> userEntity = userDAO.findById(userId);
		UserDTO userEntityDTO = getDTO(userEntity.get());
		return userEntityDTO;
	}
}
