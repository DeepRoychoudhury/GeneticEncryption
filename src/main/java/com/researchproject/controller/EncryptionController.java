package com.researchproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.researchproject.model.Encrypt;
import com.researchproject.services.EncryptionService;

@RestController
@RequestMapping("/api/encrypt/")
public class EncryptionController {

	@Autowired
	EncryptionService encryptData = new EncryptionService();
	
	@PostMapping(path="/data")
	@ResponseStatus(HttpStatus.CREATED)
	public Encrypt fetchText(@RequestBody Encrypt encrypt) throws Exception {
		return encryptData.encryptTheData(encrypt);
	}
}
