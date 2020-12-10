package com.researchproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.researchproject.model.Users;
import com.researchproject.services.UserRegistration;

@RestController
@RequestMapping("/api/registeruser/")
public class RegisterUser {

	@Autowired
	UserRegistration userReg;
	
	@PostMapping(path="/")
	@ResponseStatus(HttpStatus.CREATED)
	public String fetchText(@RequestBody Users users) throws Exception {
		boolean isUserRegistered = userReg.RegisterUser(users);
		if(isUserRegistered == true) {
			return "User Created";
		}
		return "User Creation Unsuccessfull";
	}
}
