package com.researchproject.algorithm;

import java.util.List;

class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int val){
		this.val = val;
	}
	public TreeNode(int val, TreeNode left, TreeNode right) {
		super();
		this.val = val;
		this.left = left;
		this.right = right;
	}
	
}

public class BinaryShiftingEncryption {	
	
		//constant values of patterns (enumeration)
		private enum Pattern{ 
			inorder,preorder,postorder;
		}	 
		
		//ASCII conversion of character to integer, Time Complexity: O(1)
		private int asciiValue(char data) {
			return (int) data;
		}
		
		//Adding 100th element with position
		private int addingPosition(int position, int number) {
			number=(number*100)+position;
			return number;
		}
		
		//Get Pattern type for shuffling
		private Pattern getPatternType(List<Integer> numbers) {
			int pattern = numbers.size() % 3;
			if(pattern == 0) {
				return Pattern.inorder;
			}
			else if(pattern == 1) {
				return Pattern.preorder;
			}
			return Pattern.postorder;
		}
		
		//Shuffling the new numbers w.r.t. number of elements
		private List<Integer> shuffle(Pattern patterntype, List<Integer> numbers){
			if(patterntype == Pattern.inorder) {
				inOrderSort(numbers);
			}
			else if(patterntype == Pattern.preorder){
				preOrderSort(numbers);
			}
			else {
				postOrderSort(numbers);
			}
			return numbers;			
		}
		
		//Arrangement in Pre Order Fashion (Data Structure)
		private List<Integer> preOrderSort(List<Integer> numbers){
			return numbers;			
		}
		
		//Arrangement in Post Order Fashion (Data Structure)
		private List<Integer> postOrderSort(List<Integer> numbers){
			return numbers;
		}
		
		//Arrangement in In order Fashion (Data Structure)
		private List<Integer> inOrderSort(List<Integer> numbers){
			return numbers;
		}
		
}
