package com.researchproject.services;

import org.springframework.stereotype.Service;

import com.researchproject.model.Encrypt;

@Service
public class EncryptionService {

	public Encrypt encryptTheData(Encrypt encrypt) {		
		return encrypt;
	}

}
