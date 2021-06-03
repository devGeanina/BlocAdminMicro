package com.blocadminmicro.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.blocadminmicro.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Modifying
	@Query("update User u set u.appartmentNr = ?1, u.buildingNr = ?2, u.details = ?3 where u.id = ?4")
	void updateUserAddress(int appNr, int buildingNr, String details, Long userId);
	
	@Query("SELECT u FROM User u WHERE LOWER(username)= ?1 AND LOWER(password)= ?2")
	User login(String username, String password);
}