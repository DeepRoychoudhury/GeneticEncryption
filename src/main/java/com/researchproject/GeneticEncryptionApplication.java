package com.researchproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.researchproject.model.FilesTable;
import com.researchproject.model.Group;

@SpringBootApplication
public class GeneticEncryptionApplication {

	@Bean
	public FilesTable getFiles() {
		return new FilesTable();
	}
	
	@Bean
	public Group getGroup() {
		return new Group();
	}
	
	@Bean
	public AmazonS3 awsS3Client(AWSCredentialsProvider credentialProvider, @Value("cloud.aws.region.static") String region) {
		return AmazonS3ClientBuilder.standard().withCredentials(credentialProvider).withRegion(region).build();		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GeneticEncryptionApplication.class, args);
	}

}
