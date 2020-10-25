package com.researchproject.algorithm;

import java.util.LinkedList;
import java.util.List;

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
	
		private List<Integer> ordered = new LinkedList<Integer>();
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
				preOrderArrange(node.left);
				preOrderArrange(node.right);
				ordered.add(node.val);
				}
				return ordered;	
		}
		
		//Arrangement in In order Fashion (Data Structure)
		private List<Integer> inOrderArrange(TreeNode node){
			if(node != null) {
				preOrderArrange(node.left);
				ordered.add(node.val);
				preOrderArrange(node.right);
				}
				return ordered;
		}
		
		//Inserting Numbers to TreeNode. Time-Complexity:O(n)
		private TreeNode insertToTree(List<Integer> positionedData) {
			TreeNode node = new TreeNode();			
			for(int i=0;i<positionedData.size();i++) {
			node=node.insert(node, positionedData.get(i));
			}
			return node;
		}
		
}
