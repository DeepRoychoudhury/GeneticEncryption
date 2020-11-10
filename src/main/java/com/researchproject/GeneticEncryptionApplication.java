package com.researchproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.researchproject.model.Files;

@SpringBootApplication
public class GeneticEncryptionApplication {

	@Bean
	public Files getFiles() {
		return new Files();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GeneticEncryptionApplication.class, args);
	}

}
