package com.researchproject.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.AESEncryption;
import com.researchproject.algorithm.BinaryShiftingEncryption;
import com.researchproject.algorithm.GeneticEncryptionAlgorithm;
import com.researchproject.model.Encrypt;
import com.researchproject.model.FilesTable;
import com.researchproject.repository.AwsS3Repository;
import com.researchproject.repository.AzureBlobAdapter;
import com.researchproject.repository.EncryptionRepository;

@Service
public class EncryptionService {		
	
	@Autowired
	FilesTable file;
	
	@Autowired
	FilesService fileservice;
	
	@Autowired
	AwsS3Repository awsS3repo;
	
	@Value("${deep.geneticEncryption.bucketName}") 
	private String bucketName;
	
	@Autowired
	AzureBlobAdapter azureRepo;
	
	@Value("${blob}")
	String blobName; 
	
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
		System.out.println(AESEncrypted);
		//outputEncryptedFile(AESEncrypted,encrypt.getUser_id(),encrypt.getGroup_id(),encrypt.getFileid());
		
		  boolean isDataEntered = enterData(encrepo.fetchGroupId(encrypt.getGroup_name()),encrepo.fetchDataOwnerId(encrypt.getUser_name()),encrypt.getFile_name()); 
		  if(isDataEntered) {
		  outputEncryptedFile(AESEncrypted,file.getFile_name()); 
		  }
		  else {
			  System.out.println("Record not saved in Files Database.");
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
		//BufferedWriter encryptionWriting = new BufferedWriter(new FileWriter()); 
		/*
		 * File file = new File(file_name+".txt"); FileWriter fileToSave = new
		 * FileWriter(file); fileToSave.write(AESOutput); fileToSave.close();
		 */
		azureRepo.upload(AESOutput, file_name, blobName);
		//awsS3repo.saveFileToS3(bucketName, AESOutput ,file_name+".txt");
		//encryptionWriting.close();
		System.out.println("Encryption Successful");
	}
	
}
