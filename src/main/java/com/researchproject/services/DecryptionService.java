package com.researchproject.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.BinaryShiftingDecryption;
import com.researchproject.algorithm.GeneticDecryptionAlgorithm;
import com.researchproject.model.Decrypt;
import com.researchproject.repository.AzureBlobAdapter;
import com.researchproject.repository.DecryptionRepository;

@Service
public class DecryptionService {

	@Autowired
	AzureBlobAdapter azureRepo;
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DecryptionService.class);
			
	DecryptionRepository decryptRepo = new DecryptionRepository();
	
	private String encryptedText;
	
	AESDecryption aesd = new AESDecryption();
	GeneticDecryptionAlgorithm gda = new GeneticDecryptionAlgorithm();
	BinaryShiftingDecryption bsd = new BinaryShiftingDecryption();
	
	//Using Template Design Pattern
	public String decryptCypherText(Decrypt decrypt, String filename) throws IOException, ClassNotFoundException, SQLException {
		boolean user = checkUser(decrypt,filename);
		if(user == true) {
		fetchFileFromAzure(filename);
		System.out.println("Inside Decryption Service with user name : "+decrypt.getUser_name());
		logger.info("Inside Decryption Service with user name : "+decrypt.getUser_name());
		//String aesDecrypted = aesd.decrypt(encryptedText, decrypt.getGroup_name(), decrypt.getFile_name());
		
		String aesDecrypted = aesd.decrypt(encryptedText, decrypt.getGroup_name(), decrypt.getFile_name());		
		System.out.println("AES Decryption : "+aesDecrypted);
		logger.info("AES Decryption : "+aesDecrypted);
		
		String geneticDecrypted = gda.geneticDecryption(aesDecrypted,decrypt.getFile_name());
		System.out.println("Genetic Decryption : "+geneticDecrypted);
		logger.info("Genetic Decryption : "+geneticDecrypted);
		String decryptedText = bsd.decryptBinary(geneticDecrypted);
		System.out.println("Final Decryption : "+decryptedText);
		logger.info("Final Decryption : "+decryptedText);
		//outputDecryptedFile(decryptedText);
		return decryptedText;
		}
		return "User not authorized";
	}
	
	private void outputDecryptedFile(String AESOutput) throws IOException {
		
		BufferedWriter decryptionWriting = new BufferedWriter(new FileWriter("Decrypted.txt")); 
		
		decryptionWriting.write(AESOutput); 
		decryptionWriting.close();
		System.out.println("Decryption Successful");
	}

	private void fetchFileFromAzure(String file_name) throws IOException {
		//BufferedWriter encryptionWriting = new BufferedWriter(new FileWriter()); 
		/*
		 * File file = new File(file_name+".txt"); FileWriter fileToSave = new
		 * FileWriter(file); fileToSave.write(AESOutput); fileToSave.close();
		 */
		encryptedText = azureRepo.getFile(file_name);
		//awsS3repo.saveFileToS3(bucketName, AESOutput ,file_name+".txt");
		//encryptionWriting.close();
		System.out.println("Fetching successfull.");
	}
	
	private boolean checkUser(Decrypt decrypt, String filename) {
		boolean value = false;
		value = decryptRepo.checkUserGroup(decrypt, filename);
		return value;
	}
}
