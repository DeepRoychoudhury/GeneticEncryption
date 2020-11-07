package com.researchproject.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.AESEncryption;
import com.researchproject.algorithm.BinaryShiftingEncryption;
import com.researchproject.algorithm.GeneticEncryptionAlgorithm;
import com.researchproject.model.Encrypt;

@Service
public class EncryptionService {		
	BinaryShiftingEncryption bse = new BinaryShiftingEncryption();	
	GeneticEncryptionAlgorithm gea = new GeneticEncryptionAlgorithm();
	AESEncryption aes = new AESEncryption();
	AESDecryption aesd = new AESDecryption();
	
	//Using Template Design Pattern to Encrypt the data
	public Encrypt encryptTheData(Encrypt encrypt) throws Exception {
		
		List<Integer> treeTraversed = bse.treeList(encrypt.getData());
		String geneticEncryption = gea.geneticEncryption(treeTraversed, encrypt.getUser_id(), 2); // change the accessibility Id
		String AESEncrypted = aes.encrypt(geneticEncryption,"DataConsumer","DataOwner");
		//System.out.println(AESEncrypted);
		outputEncryptedFile(AESEncrypted);
		//String AESDecryption = aesd.decrypt(AESEncrypted,"DataConsumer","DataOwner");
		//System.out.println(AESDecryption);
		return encrypt;
	}

	private void outputEncryptedFile(String AESOutput) throws IOException {
		BufferedWriter encryptionWriting = new BufferedWriter(new FileWriter("Encrypted.txt")); 
		encryptionWriting.write(AESOutput); 
		encryptionWriting.close();
		System.out.println("Encryption Successful");
	}
	
}
