package com.researchproject.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

//Tree Class to create binary tree
class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	
	//constructor for TreeNode without arguments
	public TreeNode() {	}
	TreeNode(int val){
		this.val = val;
	}
	//constructor for TreeNode with arguments
	public TreeNode(int val, TreeNode left, TreeNode right) {
		super();
		this.val = val;
		this.left = left;
		this.right = right;
	}
	
	//Create node if node is null
	private TreeNode createTreeNode(int val) {
		TreeNode node = new TreeNode();
		node.val=val;
		node.left=null;
		node.right=null;
		return node;
	} 
	
	//Inserting as a Binary Search Tree
	public TreeNode insert(TreeNode node, int val){
		if(node==null){
			return createTreeNode(val);
		}
		if(val<node.val){
			node.left=insert(node.left,val);
		}
		else if(val>node.val){
			node.right=insert(node.right,val);
		}
		return node;
	}
}


public class BinaryShiftingEncryption {	
	
		private int nodeCount = 0;
		private List<String> splittedData = new ArrayList<String>();
		private List<Integer> ManipulatedAscii = new ArrayList<Integer>();
		private List<Integer> positionedAscii = new ArrayList<Integer>();
		private List<Integer> ordered = new LinkedList<Integer>();
		
		//External method for call 
		//Template Design Pattern
		public List<Integer> treeList(String data){
			splittingData(data);
			System.out.println("Splitted Data : "+splittedData.toString());
			ComputingASCII(splittedData);
			System.out.println("ASCII Computation : "+ManipulatedAscii.toString());
			positionTrainer(ManipulatedAscii);
			System.out.println("ASCII postioned : "+positionedAscii.toString());
			convertedTree(positionedAscii);			
			System.out.println("Tree Traversed output : "+ordered.toString());
			return ordered;
		}
		
		//constant values of patterns (enumeration)
		private enum Pattern{ 
			inorder,preorder,postorder;
		}	 
		
		//Split the Data in List<String> for manipulation
		private List<String> splittingData(String data){
		for(String val : data.split(" ")) {
			splittedData.add(val);
		}
		return splittedData;
		}
		
		
		//Computing the ASCII value of each Data or String char
		private List<Integer> ComputingASCII(List<String> splittedData){
			for(int i=0;i<splittedData.size();i++) {
				for(int j=0;j<splittedData.get(i).length();j++) {
					ManipulatedAscii.add(asciiValue(splittedData.get(i).charAt(j)));
				}	
				ManipulatedAscii.add(null);
			}
			return ManipulatedAscii;
		}
		
		//ASCII conversion of character to integer, Time Complexity: O(1)
		private int asciiValue(char data) {
			return (int) data;
		}
		
		//Position Trainer to identify the position of number and 
		private List<Integer> positionTrainer(List<Integer> ManipulatedAscii){
			int pos = 0;
			for(int i=0;i<ManipulatedAscii.size();i++) {				
				if(ManipulatedAscii.get(i) != null) {
					pos++;
					positionedAscii.add(addingPosition(pos,ManipulatedAscii.get(i)));
				}
				else {
					positionedAscii.add(null);
					pos = 0; //Making position '0' whenever null encounters
				}
			}
			return positionedAscii;
		}
		
		//Adding 100th element with position
		private int addingPosition(int position, int number) {
			number=(number*100)+position;
			return number;
		}
		
		//Fetching Pattern and converting individual string to BST tree node
		//and output the result as list in preorder, postorder or inorder
		private List<Integer> convertedTree(List<Integer> positionedAscii) {
			List<Integer> numbers = new ArrayList<Integer>();
			for(int i=0;i<positionedAscii.size();i++) {
				if(positionedAscii.get(i) != null) {
					numbers.add(positionedAscii.get(i));
				}
				else {
					shuffle(getPatternType(numbers),insertToTree(numbers));	
					ordered.add(null);
					numbers.clear();
				}				
			}
			return ordered;
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
		private void shuffle(Pattern patterntype, TreeNode node){
			if(patterntype == Pattern.inorder) {
				inOrderArrange(node);
			}
			else if(patterntype == Pattern.preorder){
				preOrderArrange(node);
			}
			else {
				postOrderArrange(node);
			}			
		}
		
		//Arrangement in Pre Order Fashion (Data Structure)
		private List<Integer> preOrderArrange(TreeNode node){			
			if(node != null) {
			ordered.add(node.val);
			preOrderArrange(node.left);
			preOrderArrange(node.right);
			}
			return ordered;			
		}
		
		//Arrangement in Post Order Fashion (Data Structure)
		private List<Integer> postOrderArrange(TreeNode node){
			if(node != null) {
				postOrderArrange(node.left);
				postOrderArrange(node.right);
				ordered.add(node.val);
				}
				return ordered;	
		}
		
		//Arrangement in In order Fashion (Data Structure)
		private List<Integer> inOrderArrange(TreeNode node){
			if(node != null) {
				inOrderArrange(node.left);
				ordered.add(node.val);
				inOrderArrange(node.right);
				}
				return ordered;
		}
		
		//Inserting Numbers to TreeNode. Time-Complexity:O(n)
		private TreeNode insertToTree(List<Integer> positionedData) {
			TreeNode node = new TreeNode(positionedData.get(0));			
			for(int i=1;i<positionedData.size();i++) {
			node=node.insert(node, positionedData.get(i));
			nodeCount++;
			}
			return node;
		}
		
		//Identifies the size of Node
		private int size() {
			return nodeCount;
		}
}
