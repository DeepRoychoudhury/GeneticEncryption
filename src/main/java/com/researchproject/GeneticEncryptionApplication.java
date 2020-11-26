package com.researchproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.azure.storage.blob.BlobClientBuilder;
import com.researchproject.model.FilesTable;
import com.researchproject.model.Group;

@SpringBootApplication
public class GeneticEncryptionApplication {

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;
	
	@Value("${cloud.aws.credentials.session-token}")
	private String sessionToken;
	
    @Value("${spring.cloud.azure.storage.connection-string}")
    String connectionString;

    @Value("${blob}")
    String containerName;
    
	@Bean
	public FilesTable getFiles() {
		return new FilesTable();
	}
	
	@Bean
	public Group getGroup() {
		return new Group();
	}
	
	/*
	 * @Bean public AmazonS3
	 * awsS3Client(@Value("cloud.aws.region.use-default-aws-region-chain") String
	 * region) { //return
	 * AmazonS3ClientBuilder.standard().withCredentials(credentialProvider).
	 * withRegion(region).build(); //AWSCredentials awscred = new
	 * BasicSessionCredentials(awsAccessKey, awsSecretKey, sessionToken);
	 * BasicSessionCredentials basicSessionCredentials = new
	 * BasicSessionCredentials(this.awsAccessKey, this.awsSecretKey,
	 * this.sessionToken); return
	 * AmazonS3ClientBuilder.standard().withCredentials(new
	 * AWSStaticCredentialsProvider(basicSessionCredentials)).withRegion(Regions.
	 * US_EAST_1).build(); //AmazonS3 s3client = new AmazonS3Client(awscred);
	 * //return s3client; }
	 */
	
	@Bean
    public BlobClientBuilder getClient() {
        BlobClientBuilder client = new BlobClientBuilder();
        client.connectionString(connectionString);
        client.containerName(containerName);
        return client;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(GeneticEncryptionApplication.class, args);
	}

}
