package com.researchproject.algorithm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DeadlockLoserDataAccessException;

import com.researchproject.repository.DecryptionRepository;

public class GeneticDecryptionAlgorithm {

	String geneticDecrypted = null;
	String splitByFourteen = null;
	int cross = 0;
	List<String> mutatedString = new ArrayList<String>();
	String finalGeneticResult = null;
	List<Integer> asciiConverted = new ArrayList<Integer>();
	
	DecryptionRepository decryptRepo = new DecryptionRepository();
			
	//Template Design Pattern
	public String geneticDecryption(String aesDecrypted, String file_name) throws ClassNotFoundException, SQLException {
		mutatedString.clear();
		asciiConverted.clear();
		int dataOwnerId = decryptRepo.getDataOwnerUserId(file_name);
		int dataConsumerId = decryptRepo.getGroupId(file_name);
		splitter(aesDecrypted);
		System.out.println("Before Mutation Decryption : "+splitByFourteen);
		crossOverForDecryption(splitByFourteen,dataOwnerId,dataConsumerId);
		crossandMutation(splitByFourteen);
		System.out.println("After Mutation Decryption : "+finalGeneticResult);
		splitter(finalGeneticResult);
		geneticDecrypted = convertToASCII(splitByFourteen).toString();
		return geneticDecrypted;
	}
	
	//Convert to ASCII
	private List<Integer> convertToASCII(String finalGeneticResult){
		String[] splittedBinary = finalGeneticResult.replace("[","").replace("]","").split(", ");
		for(int i=0;i<splittedBinary.length;i++) {
		asciiConverted.add(Integer.parseInt(splittedBinary[i],2));
		}
		return asciiConverted;
	}
	
	//Splitting by 14th term
	private String splitter(String aesDecrypted){
		List<String> bitCollection = new ArrayList<String>();
		List<Character> singleNumber = new ArrayList<Character>() ;
		int j=0;
		aesDecrypted = aesDecrypted.replaceAll(" ", "");
		for(int i=0;i<aesDecrypted.length();i++) {
			if(j == 13) {
				singleNumber.add(aesDecrypted.charAt(i));
				bitCollection.add(singleNumber.toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", ""));
				j = 0;
				singleNumber.clear();
			}
			else {
				singleNumber.add(aesDecrypted.charAt(i));
				j++;
			}
		}
		System.out.println(bitCollection.toString());
		splitByFourteen = bitCollection.toString();
		return splitByFourteen;
	}
	
	//Finding out crossover
	private int crossOverForDecryption(String splitByFourteen,int dataOwnerId,int dataConsumerId) {
		String[] splittedbinary = splitByFourteen.split(", ");
		List<Integer> values = new ArrayList<Integer>();
		values.add(dataOwnerId);
		values.add(dataConsumerId);
		values.add(splittedbinary.length + 1);
		System.out.println("Cross Over values : "+values.get(0)+""+values.get(1)+""+values.get(2));

		cross = crossovernumber(values);

		System.out.println("Cross Number is : "+cross);
		return cross;
	}
	
	//Generate pseudo random number using multiplicative congruential generator(MCG) 
		//or Lehmer RNG {X(n)=((X(i)*b) modulo m}, Time Complexity : O(1)
		private int crossovernumber(List<Integer> values) {
			return (((values.get(0) * values.get(1)) + values.get(2)) % 3);
		}
		
		//crossandmutation initializer
		private String crossandMutation(String splitByFourteen) {
			List<Integer> firstbinary = new ArrayList<Integer>();
			List<Integer> secondbinary = new ArrayList<Integer>();
			List<String> mutatedResult = new ArrayList<String>();
			String[] splitted = splitByFourteen.replace(",","").split(" ");
			int i=0;
			int k=0;
			while(k<(splitted.length/2)) {	
				List<String> sizeEqualizedStrings = new ArrayList<String>();
				//List<Integer> firstbinary = new ArrayList<Integer>();
				//List<Integer> secondbinary = new ArrayList<Integer>();
				String firstbinarystring = null;
				String secondbinarystring = null;
				List<Character> firstbinaryCharacter = new ArrayList<Character>();
				List<Character> secondbinaryCharacter = new ArrayList<Character>();
				
				firstbinarystring = splitted[i].replace("[", "").replace("]", "");
				secondbinarystring = splitted[i+1].replace("[", "").replace("]", "");
								
				for(int j=0; j<firstbinarystring.length();j++) {
					firstbinaryCharacter.add(firstbinarystring.charAt(j));
					secondbinaryCharacter.add(secondbinarystring.charAt(j));
				}
				firstbinary = convertToInteger(firstbinaryCharacter);
				secondbinary = convertToInteger(secondbinaryCharacter);
				
				mutatedString.add(mutation(cross,firstbinary,secondbinary).toString());
				
				i+=2;	
				k++;
			}
			finalGeneticResult = mutatedString.toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
			return finalGeneticResult;
		}
		
		//Converting String List to integer List
		private List<Integer> convertToInteger(List<Character> stringToBeConverted){		
			List<Integer> converted = new ArrayList<Integer>();
			for(int i=0;i<stringToBeConverted.size();i++) {
				converted.add(Integer.parseInt(String.valueOf(stringToBeConverted.get(i))));
			}
			return converted;
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
