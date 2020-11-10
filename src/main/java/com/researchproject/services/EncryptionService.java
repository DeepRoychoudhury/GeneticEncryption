package com.researchproject.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.AESEncryption;
import com.researchproject.algorithm.BinaryShiftingEncryption;
import com.researchproject.algorithm.GeneticEncryptionAlgorithm;
import com.researchproject.model.Encrypt;
import com.researchproject.model.Files;
import com.researchproject.repository.EncryptionRepository;

@Service
public class EncryptionService {		
	
	@Autowired
	Files file;
	
	@Autowired
	FilesService fileservice;
	
	EncryptionRepository encrepo = new EncryptionRepository();	
	BinaryShiftingEncryption bse = new BinaryShiftingEncryption();	
	GeneticEncryptionAlgorithm gea = new GeneticEncryptionAlgorithm();
	AESEncryption aes = new AESEncryption();
	AESDecryption aesd = new AESDecryption();
	
	//Using Template Design Pattern to Encrypt the data
	public Encrypt encryptTheData(Encrypt encrypt) throws Exception {
		
		List<Integer> treeTraversed = bse.treeList(encrypt.getData());
		String geneticEncryption = gea.geneticEncryption(treeTraversed, encrypt.getUser_name(), encrypt.getGroup_name()); 
		String AESEncrypted = aes.encrypt(geneticEncryption,encrypt.getGroup_name(),encrypt.getPassword());
		//System.out.println(AESEncrypted);
		//outputEncryptedFile(AESEncrypted,encrypt.getUser_id(),encrypt.getGroup_id(),encrypt.getFileid());
		
		  boolean isDataEntered = enterData(encrepo.fetchGroupId(encrypt.getGroup_name()),encrepo.fetchDataOwnerId(encrypt.getUser_name()),encrypt.getFile_name()); 
		  if(isDataEntered) {
		  outputEncryptedFile(AESEncrypted,file.getFile_name()); 
		  }
		  else {
			  System.out.println("Record not saved in Files.");
		  }
		//String AESDecryption = aesd.decrypt(AESEncrypted,"DataConsumer","DataOwner");
		//System.out.println(AESDecryption);
		return encrypt;
	}

	private boolean enterData(int group_id, int user_id, String file_name) {
		boolean isRecordInserted = false;
		file.setGroup_id(group_id);
		file.setUser_id(user_id);
		file.setFile_name(file_name);
		isRecordInserted = fileservice.enterFileRecord(file);
		if(isRecordInserted) {
			return true;
		}
		return false;
	}

	private void outputEncryptedFile(String AESOutput, String file_name) throws IOException {
		BufferedWriter encryptionWriting = new BufferedWriter(new FileWriter("Encrypted_"+file_name+".txt")); 
		encryptionWriting.write(AESOutput); 
		encryptionWriting.close();
		System.out.println("Encryption Successful");
	}
	
}
