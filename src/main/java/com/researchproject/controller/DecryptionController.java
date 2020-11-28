/**
 * 
 * @author Deep Roychoudhury
 *
 *
 */
package com.researchproject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.researchproject.model.Decrypt;
import com.researchproject.services.DecryptionService;

@RestController
@RequestMapping("/api/decrypt/")
public class DecryptionController {

	Decrypt decrypt = new Decrypt();
	
	@Autowired
	DecryptionService decryptData = new DecryptionService();
	
	
	  @PostMapping(path="/data") 
	  public ResponseEntity<Object> fetchText(@RequestBody Decrypt decrypt) throws Exception { 
		  String output = decryptData.decryptCypherText(decrypt, decrypt.getFile_name()); 
		  //return new File(output); 
		  return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" ).body(output);
	  }
	  
	  @GetMapping(path="/data/{user_name}/{password}/{group_name}/{file_name}") 
	  public ResponseEntity<Object> fetchingText(@PathVariable(name = "user_name") String user_name,@PathVariable(name = "password") String password,@PathVariable(name = "group_name") String group_name,@PathVariable(name = "file_name") String file_name) throws Exception { 
		  decrypt.setUser_name(user_name);
		  decrypt.setPassword(password);
		  decrypt.setGroup_name(group_name);
		  decrypt.setFile_name(file_name);
		  String output = decryptData.decryptCypherText(decrypt, decrypt.getFile_name()); 
		  //return new File(output); 
		  return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" ).body(output);
	  }
}
