package com.researchproject.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShannonEntropy {

	public List<Double> calculationsForEntropy(String value){
		List<Double> values = new ArrayList<Double>();
		values = calculateShannonEntropy(value);
		return values;
	}
	
	private List<Double> calculateShannonEntropy(String value) {
		List<Double> entropyAndFreq = new ArrayList<Double>();
		double entropy = 0.0, probability = 0.0;
		HashMap<Character, Integer> frequencyCount = new HashMap<Character, Integer>();
		frequencyCount = countFrequency(value);
		
		for(int i=0;i<value.length();i++){
			double probabilityValue = 1.0 * frequencyCount.get(value.charAt(i)) / value.length();
			probability = probabilityValue;
			if(frequencyCount.get(value.charAt(i)) > 0) entropy -= probabilityValue * Math.log(probabilityValue) / Math.log(2);
		}
		entropyAndFreq.add(entropy);
		entropyAndFreq.add(probability);
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
