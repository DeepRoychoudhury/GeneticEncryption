package com.researchproject.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.researchproject.algorithm.BinaryShiftingEncryption;
import com.researchproject.algorithm.GeneticEncryptionAlgorithm;
import com.researchproject.model.Encrypt;

@Service
public class EncryptionService {		
	BinaryShiftingEncryption bse = new BinaryShiftingEncryption();	
	GeneticEncryptionAlgorithm gea = new GeneticEncryptionAlgorithm();
	
	//Using Template Design Pattern to Encrypt the data
	public Encrypt encryptTheData(Encrypt encrypt) {
		List<Integer> treeTraversed = bse.treeList(encrypt.getData());
		gea.geneticEncryption(treeTraversed);
		return encrypt;
	}

}
