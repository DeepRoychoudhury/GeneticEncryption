package com.researchproject.algorithm;

import java.util.ArrayList;
import java.util.List;

public class EncryptionAlgorithm {

	//Visible encryption method
	public String encrypt(String data) {
		return null;
	}
	
	//Bit calculation of integer n, Time Complexity: O(1)
	private int BitManipulation(int n) {		
		return Integer.bitCount(n);		
	}
	
	//ASCII conversion of character to integer, Time Complexity: O(1)
	private int AsciiValue(char data) {
		return (int) data;
	}
	
	//Generate pseudo random number using multiplicative congruential generator(MCG) 
	//or Lehmer RNG {X(i+1)=(X(i)*a) modulo m}, Time Complexity : O(1)
	private int crossovernumber(List<Integer> values) {
		return ((values.get(0) * values.get(1)) % (values.get(2)));
	}
	
	//Perform mutation of adjacent binary numbers for crossovers {2,1 and 0}, Time Complexity: Best Case: O(1), Worst Case: O(4)
	private static List<List<Integer>> mutation(int cross, List<Integer> firstbinary, List<Integer> secondbinary){
		List<List<Integer>> mutatedValue = new ArrayList<List<Integer>>();
		if(cross==2) {
			for(int i=2;i<=5;i++) {
				int temp = firstbinary.get(i);
				firstbinary.set(i, secondbinary.get(i));
				secondbinary.set(i, temp);
			}
		}
		else if(cross==1) {
			for(int i=0;i<4;i++) {
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
		firstbinary.add(cross);
		secondbinary.add(cross);
		mutatedValue.add(firstbinary);
		mutatedValue.add(secondbinary);
		return mutatedValue;
	}
	
}
