package com.researchproject.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.BinaryShiftingDecryption;
import com.researchproject.algorithm.GeneticDecryptionAlgorithm;
import com.researchproject.model.Decrypt;

@Service
public class DecryptionService {

	AESDecryption aesd = new AESDecryption();
	GeneticDecryptionAlgorithm gda = new GeneticDecryptionAlgorithm();
	BinaryShiftingDecryption bsd = new BinaryShiftingDecryption();
	
	//Using Template Design Pattern
	public Decrypt decryptCypherText(Decrypt decrypt) throws IOException {
		System.out.println("Inside Decryption Service with user id : "+decrypt.getUser_id());
		String aesDecrypted = aesd.decrypt(decrypt.getData(), decrypt.getPassword(), decrypt.getGroup_id());
		System.out.println("AES Decryption : "+aesDecrypted);
		String geneticDecrypted = gda.geneticDecryption(aesDecrypted,1,2);
		String decryptedText = bsd.decryptBinary(geneticDecrypted);
		outputDecryptedFile(decryptedText);
		return decrypt;
	}
	
	private void outputDecryptedFile(String AESOutput) throws IOException {
		BufferedWriter decryptionWriting = new BufferedWriter(new FileWriter("Decrypted.txt")); 
		decryptionWriting.write(AESOutput); 
		decryptionWriting.close();
		System.out.println("Decryption Successful");
	}
}
