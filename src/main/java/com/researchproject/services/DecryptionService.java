package com.researchproject.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.BinaryShiftingDecryption;
import com.researchproject.algorithm.GeneticDecryptionAlgorithm;
import com.researchproject.model.Decrypt;
import com.researchproject.repository.AzureBlobAdapter;

@Service
public class DecryptionService {

	@Autowired
	AzureBlobAdapter azureRepo;
	
	private String encryptedText;
	
	AESDecryption aesd = new AESDecryption();
	GeneticDecryptionAlgorithm gda = new GeneticDecryptionAlgorithm();
	BinaryShiftingDecryption bsd = new BinaryShiftingDecryption();
	
	//Using Template Design Pattern
	public Decrypt decryptCypherText(Decrypt decrypt, String filename) throws IOException, ClassNotFoundException, SQLException {
		//fetchFileFromAzure(filename);
		System.out.println("Inside Decryption Service with user name : "+decrypt.getUser_name());
		
		//String aesDecrypted = aesd.decrypt(encryptedText, decrypt.getGroup_name(), decrypt.getFile_name());
		
		String aesDecrypted = aesd.decrypt(decrypt.getData(), decrypt.getGroup_name(), decrypt.getFile_name());
		
		System.out.println("AES Decryption : "+aesDecrypted);
		String geneticDecrypted = gda.geneticDecryption(aesDecrypted,decrypt.getFile_name());
		System.out.println("Genetic Decryption : "+geneticDecrypted);
		String decryptedText = bsd.decryptBinary(geneticDecrypted);
		System.out.println("Final Decryption : "+decryptedText);
		outputDecryptedFile(decryptedText);
		return decrypt;
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
}
