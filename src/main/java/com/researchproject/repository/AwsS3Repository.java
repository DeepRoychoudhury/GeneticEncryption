package com.researchproject.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Repository
public class AwsS3Repository {

	@Autowired
	AmazonS3Client awsS3Client;
	
	public void saveFileToS3(String bucketName, File file, String fileName) {
		awsS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}

	public S3Object fetchFileFromS3(String bucketName, String fileName) {
		S3Object obj = awsS3Client.getObject(bucketName, fileName);
		return obj;
	}
	
}
