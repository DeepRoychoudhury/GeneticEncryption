package com.researchproject.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

public class BinaryShiftingDecryption {

	String decryptedText = null;
	String splittedText = null;
	List<String> sortedString = new ArrayList<String>();
	
	
	public String decryptBinary(String geneticDecryption) {
		sortedString.clear();
		splittingDecrypted(geneticDecryption.replace("[", "").replace("]", ""));
		
		convertToCharacter(sortedString.toString());
		System.out.println("Final Decrypted Text : "+decryptedText);
		return decryptedText;
	}

	//Convert to Character
	private String convertToCharacter(String splittedText2) {	
		List<Integer> listNumbers = new ArrayList<Integer>();
		String text = splittedText2.replace("[","").replace("]", "").replace(", ", " ");
		String[] textArray = text.split(" ");
		List<Character> listCharacters = new ArrayList<Character>();
		
		for(int i=0;i<textArray.length;i++) {	
			if(textArray[i] != " " && textArray[i] != "") {
			listNumbers.add(Integer.parseInt(textArray[i]));
			}
		}
		for(int j=0;j<listNumbers.size();j++) {
			if(listNumbers.get(j) != 0) {
				int asciiToInt = listNumbers.get(j)/100;
				char asciiToCharacter = (char)asciiToInt;
				listCharacters.add(asciiToCharacter);
			}
			else {
				listCharacters.add(' ');
			}
		}
		decryptedText = listCharacters.toString().replace(", ", "").replace("[", "").replace("]", "");
		return decryptedText;		
	}

	//Splitting the Decrypted Text
	private String splittingDecrypted(String splitted) {
		List<Integer> decryptedString = new ArrayList<Integer>();
		String[] splittingComma = splitted.toString().split(", "); 
		for(int i=0;i<splittingComma.length;i++) {
			if((splittingComma[i].equals("0")) || (i == (splittingComma.length - 1))) {
				if(i == (splittingComma.length - 1)) {
					decryptedString.add(Integer.parseInt(splittingComma[i]));
				}
				sortedString.add(sortingTheStrings(decryptedString));
				sortedString.add("0");
				decryptedString.clear();
			}
			else {
				decryptedString.add(Integer.parseInt(splittingComma[i]));
			}
		}
		splittedText = decryptedString.toString();
		return splittedText;
	}

	//Merge sort for sorting
	private String sortingTheStrings(List<Integer> decryptedString) {
		String sorted = null;
		if(decryptedString.size() > 1) {
		sorted = sort(decryptedString, 0, decryptedString.size() - 1);
		}
		else {
			sorted = decryptedString.toString();
		}
		return sorted;
	}
	
	private String sort(List<Integer> sortingString,int lower,int higher) {
		String sorted = null;
		if(lower<higher) {
		int middle = (lower + higher)/2;
		sort(sortingString,lower,middle);
		sort(sortingString,middle+1,higher);
		sorted = merge(sortingString,lower,middle,higher).toString();
		}
		return sorted;
	}

	private List<Integer> merge(List<Integer> sortingString, int lower, int middle, int higher) {
		int sizeOfFirstList = middle - lower + 1;	
		int sizeOfSecondList = higher - middle;
		
		int[] left = new int[sizeOfFirstList];
		int[] right = new int[sizeOfSecondList];
		
		for(int i=0;i<sizeOfFirstList;i++) {
			left[i] = sortingString.get(lower + i);
		}
		for(int j=0;j<sizeOfSecondList;j++) {
			right[j] = sortingString.get(middle + 1 + j);
		}
		
		int i=0;
		int j=0;
		int k = lower;
		
		while(i < sizeOfFirstList && j < sizeOfSecondList) {
			if((left[i] % 100) <= (right[j] % 100)) {
				sortingString.set(k, left[i]);
				i++;
			}
			else {
				sortingString.set(k, right[j]);
				j++;
			}
			k++;
		}
		while(i < sizeOfFirstList) {
			sortingString.set(k, left[i]);
			i++;
			k++;
		}
		while(j < sizeOfSecondList) {
			sortingString.set(k, right[j]);
			j++;
			k++;
		}
		return sortingString;
	} 
}
