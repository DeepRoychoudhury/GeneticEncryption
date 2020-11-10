package com.researchproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.researchproject.model.Files;
import com.researchproject.model.Group;

@SpringBootApplication
public class GeneticEncryptionApplication {

	@Bean
	public Files getFiles() {
		return new Files();
	}
	
	@Bean
	public Group getGroup() {
		return new Group();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GeneticEncryptionApplication.class, args);
	}

}
