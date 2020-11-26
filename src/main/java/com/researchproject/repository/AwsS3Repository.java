/*
 * package com.researchproject.repository;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.amazonaws.services.s3.AmazonS3Client; import
 * com.amazonaws.services.s3.model.S3Object;
 * 
 * @Repository public class AwsS3Repository {
 * 
 * @Autowired AmazonS3Client awsS3Client;
 * 
 * public void saveFileToS3(String bucketName, String file, String fileName) {
 * //S3Object obj = new S3Object(fileName, file);
 * awsS3Client.putObject(bucketName, fileName, file); }
 * 
 * public S3Object fetchFileFromS3(String bucketName, String fileName) {
 * S3Object obj = awsS3Client.getObject(bucketName, fileName); return obj; }
 * 
 * }
 */