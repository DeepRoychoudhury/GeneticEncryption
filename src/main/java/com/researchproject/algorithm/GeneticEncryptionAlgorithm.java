package com.researchproject.algorithm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.researchproject.model.Encrypt;
import com.researchproject.repository.EncryptionRepository;

public class GeneticEncryptionAlgorithm {

	@Autowired
	Encrypt encrypt;
	
	EncryptionRepository usersrepo = new EncryptionRepository();
	
	List<Integer> treeReplacedNull = new ArrayList<Integer>();
	List<String> bitConverted = new ArrayList<String>();
	List<String> geneticString = new ArrayList<String>();
	String geneticEncrypted = null;
	
	//Visible encryption method
	//Template Design Pattern
	public String geneticEncryption(List<Integer> treeTraversedData, String dataOwner_username, String group_name) throws ClassNotFoundException, SQLException{
		treeReplacedNull.clear();
		bitConverted.clear();
		geneticString.clear();
		geneticEncrypted = null;
		
		int dataOwnerId = usersrepo.fetchDataOwnerId(dataOwner_username);
		int accessibilityId = usersrepo.fetchGroupId(group_name);
				
		replaceNull(treeTraversedData);
		//System.out.println("Replace Null : "+treeReplacedNull.toString());
		PerformBitOperation(treeReplacedNull);
		//System.out.println("Binary conversion of ASCII : "+bitConverted.toString());
		geneticEncrypted = crossandmut(treeTraversedData, bitConverted, dataOwnerId, accessibilityId); 
		//System.out.println("Encrypted output : "+geneticEncrypted);
		return geneticEncrypted;
	}
	
	//replace null by 0 method
	private List<Integer> replaceNull(List<Integer> treeTraversedData){
		String treeTraversedString = treeTraversedData.toString().replace("[","").replace("]", "").replaceAll(",", "").replaceAll("null", "00000000000000");
		//System.out.println(treeTraversedString);
		String[] treeTraversedArray = treeTraversedString.split(" ");
		for(int i=0;i<treeTraversedArray.length;i++) {
				treeReplacedNull.add(Integer.parseInt(treeTraversedArray[i]));
		}
		return treeReplacedNull;
	}
	
	//Performing bit manipulation of each integer
	private List<String> PerformBitOperation(List<Integer> treeTraversedData){		
		for(int i=0;i<treeTraversedData.size();i++) {
			if(!treeTraversedData.get(i).equals(0)) {
			bitConverted.add(BitManipulation(treeTraversedData.get(i)));
			}
			else {
				bitConverted.add("00000000000000");
			}
		}
		return bitConverted;
	}
	
	//Bit calculation of integer n, Time Complexity: O(1)
	private String BitManipulation(int n) {		
		return Integer.toBinaryString(n);		
	}
		
	//Perform crossover and mutation
	//
	private String crossandmut(List<Integer> treeTraversedData, List<String> bitConverted, int dataOwnerId, int accessibilityId){
		int i=0;
		List<Integer> crossValue = new ArrayList<Integer>();		
		crossValue.add(dataOwnerId);
		crossValue.add(accessibilityId);
		crossValue.add(treeTraversedData.size());
		System.out.println("Cross Over values : "+crossValue.get(0)+""+crossValue.get(1)+""+crossValue.get(2));
		int cross = crossovernumber(crossValue);

		System.out.println("Cross Number is : "+cross);
		int k=0;
		while(k<(bitConverted.size()/2)) {	
			List<String> sizeEqualizedStrings = new ArrayList<String>();
			List<Integer> firstbinary = new ArrayList<Integer>();
			List<Integer> secondbinary = new ArrayList<Integer>();
			String firstbinarystring = null;
			String secondbinarystring = null;
			List<Character> firstbinaryCharacter = new ArrayList<Character>();
			List<Character> secondbinaryCharacter = new ArrayList<Character>();
			
			firstbinarystring = bitConverted.get(i).toString();
			secondbinarystring = bitConverted.get(i+1).toString();
			
			sizeEqualizedStrings = EqualizingStrings(firstbinarystring,secondbinarystring);
			
			firstbinarystring = sizeEqualizedStrings.get(0);
			secondbinarystring = sizeEqualizedStrings.get(1);
			
			for(int j=0; j<firstbinarystring.length();j++) {
				firstbinaryCharacter.add(firstbinarystring.charAt(j));
				secondbinaryCharacter.add(secondbinarystring.charAt(j));
			}
			
			firstbinary = convertToInteger(firstbinaryCharacter);
			secondbinary = convertToInteger(secondbinaryCharacter);
			
			geneticString.add(mutation(cross, firstbinary, secondbinary).toString());
			i+=2;
			k++;
		}
		System.out.println("Genetic Mutation output : "+geneticString.toString());
		return geneticString.toString().replace("[", "").replace("]", "").replace(",", "");
	}
	
	//Equalizing the size of first string and second string
	private List<String> EqualizingStrings(String firstbinarystring, String secondbinarystring){
		List<String> equalized = new ArrayList<String>();
		int max=0,min=0,difference=0;
		max = Math.max(firstbinarystring.length(), secondbinarystring.length());
		min = Math.min(firstbinarystring.length(), secondbinarystring.length());
		difference = max-min;
		while(difference!=0) {
			if(firstbinarystring.length() == max) {
				secondbinarystring = "0" + secondbinarystring;
			}
			else {
				firstbinarystring = "0" + firstbinarystring;
			}
			difference--;
		}
		
		//add 14th if less
		if(firstbinarystring.length() != 14 && secondbinarystring.length()!= 14) {
			int diff = 14 - firstbinarystring.length();
			while(diff!=0) {
				if(firstbinarystring.length() != 14) {
					firstbinarystring = "0" + firstbinarystring;
					secondbinarystring = "0" + secondbinarystring;
				}
				diff--;
			}
		}
		
		equalized.add(firstbinarystring);
		equalized.add(secondbinarystring);
		return equalized;
	}
	
	//Converting String List to integer List
	private List<Integer> convertToInteger(List<Character> stringToBeConverted){		
		List<Integer> converted = new ArrayList<Integer>();
		for(int i=0;i<stringToBeConverted.size();i++) {
			converted.add(Integer.parseInt(String.valueOf(stringToBeConverted.get(i))));
		}
		return converted;
	}
	
	//Generate pseudo random number using multiplicative congruential generator(MCG) 
	//or Lehmer RNG {X(n)=((X(i)*b) modulo m}, Time Complexity : O(1)
	private int crossovernumber(List<Integer> values) {
		return (((values.get(0) * values.get(1)) + values.get(2)) % 3);
	}
	
	//Perform mutation of adjacent binary numbers for crossovers {2,1 and 0}, Time Complexity: Best Case: O(1), Worst Case: O(4)
	private static List<List<Integer>> mutation(int cross, List<Integer> firstbinary, List<Integer> secondbinary){
		List<List<Integer>> mutatedValue = new ArrayList<List<Integer>>();
		if(cross==2) {
			for(int i=4;i<=9;i++) {
				int temp = firstbinary.get(i);
				firstbinary.set(i, secondbinary.get(i));
				secondbinary.set(i, temp);
			}
		}
		else if(cross==1) {
			for(int i=0;i<7;i++) {
				int temp = firstbinary.get(i);
				firstbinary.set(i, secondbinary.get(i));
				secondbinary.set(i, temp);
			}
		}
		else {
			List<Integer> temp = firstbinary;
			firstbinary=secondbinary;
			secondbinary=temp;
		}	
		
		//Adding the crossover at the end of binary number
		//firstbinary.add(cross);
		//secondbinary.add(cross);
		
		mutatedValue.add(firstbinary);
		mutatedValue.add(secondbinary);
		return mutatedValue;
	}
	
}
