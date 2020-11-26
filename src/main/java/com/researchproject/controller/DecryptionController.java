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

	@Autowired
	DecryptionService decryptData = new DecryptionService();
	
	
	  @PostMapping(path="/data") 
	  public ResponseEntity<Object> fetchText(@RequestBody Decrypt decrypt) throws Exception { 
		  String output = decryptData.decryptCypherText(decrypt, decrypt.getFile_name()); 
		  //return new File(output); 
		  return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" ).body(output);
	  }
	
	  @GetMapping("/downloadFile")
	      public ResponseEntity<Object> downloadFile(@RequestBody Decrypt decrypt,HttpServletRequest request) {
	         	          
	          String fileName = decrypt.getFile_name();
	          Object resource = null;
	  	if(fileName !=null && !fileName.isEmpty()) {
	        	  try {
	              resource = decryptData.decryptCypherText(decrypt, decrypt.getFile_name());
	          } catch (Exception e) {
	        	  e.printStackTrace();
	          }
	  // Try to determine file's content type
	  		String contentType = null;
	  		contentType = request.getServletContext().getMimeType(((File) resource).getAbsolutePath());
	  // Fallback to the default content type if type could not be determined
	          if(contentType == null) {
	        	  contentType = "application/octet-stream";
	          }
	  	return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" ).body(resource);
	          } 
	          else {
	          return ResponseEntity.notFound().build();
	  }
	  }
}
