package com.researchproject.algorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShannonEntropy {


	private DecimalFormat df = new DecimalFormat("0.0000");
	
	public List<String> calculationsForEntropy(String value){
		List<String> values = new ArrayList<String>();
		values = calculateShannonEntropy(value);
		return values;
	}
	
	private List<String> calculateShannonEntropy(String value) {
		List<String> entropyAndFreq = new ArrayList<String>();
		double entropy = 0.0, probability = 0.0;
		HashMap<Character, Integer> frequencyCount = new HashMap<Character, Integer>();
		frequencyCount = countFrequency(value);
		
		for(int i=0;i<value.length();i++){
			double probabilityValue = 1.0 * frequencyCount.get(value.charAt(i)) / value.length();
			probability = probabilityValue;
			if(frequencyCount.get(value.charAt(i)) > 0) entropy -= probabilityValue * Math.log(probabilityValue) / Math.log(2);
		}
		entropyAndFreq.add(df.format(entropy));
		entropyAndFreq.add(df.format(probability));
		return entropyAndFreq;
	}
	
	private HashMap<Character, Integer> countFrequency(String value){
		HashMap<Character, Integer> count = new HashMap<Character, Integer>();
		int j = 0;
		for(int i=0; i<value.length();i++) {	
			if(count.containsKey(value.charAt(i))){
				Integer freq = count.get(value.charAt(i));
				count.put(value.charAt(i), ++freq);
			}	
			else{
				count.put(value.charAt(i), 1);
			}
		}
		return count;
	}
}
