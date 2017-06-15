package edu.iastate.cs228.hw5;

import edu.iastate.cs228.hw5.SplayTree.Node;

public class Testing {

	private static Node n;

	public static void main(String[] args) throws IllegalArgumentException, AllCopiesRentedOutException {
		// TODO Auto-generated method stub
		SplayTree<Integer> tree = new SplayTree<Integer>();
		tree.addBST(6);
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(4);
		tree.addBST(3);
		tree.addBST(5);
		tree.addBST(7);

		tree.contains(24);
		//System.out.println(tree.toString().trim());
	}

}
