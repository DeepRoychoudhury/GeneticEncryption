/**
 * 
 * @author Deep Roychoudhury
 *
 *
 */
package com.researchproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.researchproject.model.Decrypt;
import com.researchproject.services.DecryptionService;

@RestController
@RequestMapping("/api/decrypt/")
public class DecryptionController {

	@Autowired
	DecryptionService decryptData = new DecryptionService();
	
	@PostMapping(path="/data")
	@ResponseStatus(HttpStatus.CREATED)
	public Decrypt fetchText(@RequestBody Decrypt decrypt) throws Exception {
		return decryptData.decryptCypherText(decrypt, decrypt.getFile_name());
	}
}
