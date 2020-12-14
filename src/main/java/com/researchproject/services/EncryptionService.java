package com.researchproject.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.algorithm.AESDecryption;
import com.researchproject.algorithm.AESEncryption;
import com.researchproject.algorithm.BinaryShiftingEncryption;
import com.researchproject.algorithm.GeneticEncryptionAlgorithm;
import com.researchproject.algorithm.ShannonEntropy;
import com.researchproject.model.Encrypt;
import com.researchproject.model.FilesTable;
import com.researchproject.repository.AzureBlobAdapter;
import com.researchproject.repository.DecryptionRepository;
import com.researchproject.repository.EncryptionRepository;

@Service
public class EncryptionService {		
	
	@Autowired
	FilesTable file;
	
	@Autowired
	FilesService fileservice;
	
	/*
	 * @Autowired AwsS3Repository awsS3repo;
	 */
		
	@Autowired
	AzureBlobAdapter azureRepo;

	@Autowired
	DecryptionRepository decryptRepo;

	List<String> returnValues = new ArrayList<String>();
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DecryptionService.class);

	ShannonEntropy shannonEntropy = new ShannonEntropy();
	EncryptionRepository encrepo = new EncryptionRepository();	
	BinaryShiftingEncryption bse = new BinaryShiftingEncryption();	
	GeneticEncryptionAlgorithm gea = new GeneticEncryptionAlgorithm();
	AESEncryption aes = new AESEncryption();
	AESDecryption aesd = new AESDecryption();
	
	//Using Template Design Pattern to Encrypt the data
	public List<String> encryptTheData(Encrypt encrypt) throws Exception { 
		returnValues.clear();
		boolean checkDataOwnerPwd = decryptRepo.CheckUserPassword(encrypt.getUser_name(), encrypt.getPassword());
		boolean checkifDataOwner = decryptRepo.CheckIfDataOwner(encrypt.getUser_name());

		if(checkDataOwnerPwd == true && checkifDataOwner == true){
		List<Integer> treeTraversed = bse.treeList(encrypt.getData());
		returnValues.add("Tree Traversed : "+treeTraversed);
		returnValues.add("Shannon Entropy Value of Tree Traversed Data is : " +shannonEntropy.calculationsForEntropy(treeTraversed.toString()).get(0));
		returnValues.add("Shannon Entropy Probability Value of Tree Traversed Data is : " +shannonEntropy.calculationsForEntropy(treeTraversed.toString()).get(1));
		logger.info("Tree Traversed : "+treeTraversed);
		logger.info("Shannon Entropy Value of Tree Traversed Data is : " +shannonEntropy.calculationsForEntropy(treeTraversed.toString()).get(0));
		logger.info("Shannon Entropy Probability Value of Tree Traversed Data is : " +shannonEntropy.calculationsForEntropy(treeTraversed.toString()).get(1));
		
		String geneticEncryption = gea.geneticEncryption(treeTraversed, encrypt.getUser_name(), encrypt.getGroup_name()); 
		returnValues.add("Genetic Encryption = "+geneticEncryption);
		returnValues.add("Shannon Entropy Value of Genetic Encrypted Data is : " +shannonEntropy.calculationsForEntropy(geneticEncryption).get(0));
		returnValues.add("Shannon Entropy Probability Value of Genetic Encrypted Data is : " +shannonEntropy.calculationsForEntropy(geneticEncryption).get(1));
		logger.info("Genetic Encryption = "+geneticEncryption);
		logger.info("Shannon Entropy Value of Genetic Encrypted Data is : " +shannonEntropy.calculationsForEntropy(geneticEncryption).get(0));
		logger.info("Shannon Entropy Probability Value of Genetic Encrypted Data is : " +shannonEntropy.calculationsForEntropy(geneticEncryption).get(1));
		
		String AESEncrypted = aes.encrypt(geneticEncryption,encrypt.getGroup_name(),encrypt.getPassword());
		returnValues.add("AES : "+AESEncrypted);
		returnValues.add("Shannon Entropy Value of AES Encrypted Data is : " +shannonEntropy.calculationsForEntropy(AESEncrypted).get(0));
		returnValues.add("Shannon Entropy Probability Value of AES Encrypted Data is : " +shannonEntropy.calculationsForEntropy(AESEncrypted).get(1));
		logger.info("AES : "+AESEncrypted);
		logger.info("Shannon Entropy Value of AES Encrypted Data is : " +shannonEntropy.calculationsForEntropy(AESEncrypted).get(0));
		logger.info("Shannon Entropy Probability Value of AES Encrypted Data is : " +shannonEntropy.calculationsForEntropy(AESEncrypted).get(1));
		//outputEncryptedFile(AESEncrypted,encrypt.getUser_id(),encrypt.getGroup_id(),encrypt.getFileid());
		
		
		  boolean isDataEntered =
		  enterData(encrepo.fetchGroupId(encrypt.getGroup_name()),encrepo.
		  fetchDataOwnerId(encrypt.getUser_name()),encrypt.getFile_name());
		  if(isDataEntered) { 
			  outputEncryptedFile(AESEncrypted,file.getFile_name()); 
			  }
		  else { 
			  logger.error("Record not saved in Files Database.");
		  returnValues.add("Record not saved in Files Database."); 
		  }
		 }
		 else {
		 	returnValues.add("Unsuccessful Encryption due to wrong credentials");
		 }
		//String AESDecryption = aesd.decrypt(AESEncrypted,"DataConsumer","DataOwner");
		//System.out.println(AESDecryption);
		return returnValues;
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
		 
		azureRepo.upload(AESOutput, file_name);
		//awsS3repo.saveFileToS3(bucketName, AESOutput ,file_name+".txt");
		
		returnValues.add("Encryption Successful");
	}
	
}
