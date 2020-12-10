package com.researchproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.model.Users;

@Service
public class UserRegistration {

	@Autowired
	UsersService userService;
	
	public boolean RegisterUser(Users users) {
		boolean isUserEntered = userService.enterNewUser(users);
		if(isUserEntered == true) return true;
		
		return false;
	}

}
