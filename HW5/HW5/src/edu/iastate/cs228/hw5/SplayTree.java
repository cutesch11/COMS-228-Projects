package edu.iastate.cs228.hw5;

import java.util.AbstractSet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Caleb Utesch

 *
 */

/**
 * 
 * This class implements a splay tree. Add any helper methods or implementation
 * details you'd like to include.
 *
 */

public class SplayTree<E extends Comparable<? super E>> extends AbstractSet<E> {
	protected Node root;
	protected int size;
	protected StringBuilder sb = new StringBuilder();

	class Node {
		public E data;
		public Node left;
		public Node parent;
		public Node right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public Node clone() {
			return new Node(data);
		}

		@Override
		public String toString() { // for debugging
			return this.data.toString();
		}
	}

	/**
	 * Default constructor constructs an empty tree.
	 */
	public SplayTree() {
		size = 0;
	}

	/**
	 * Needs to call addBST() later on to complete tree construction.
	 */
	public SplayTree(E data) {
		root = new Node(data);
		size++;
	}

	/**
	 * Copies over an existing splay tree. The entire tree structure must be
	 * copied. No splaying.
	 * 
	 * @param tree
	 */
	public SplayTree(SplayTree tree) {
		// TODO

		if (tree.root.left == null && tree.root.right == null) {
			this.root = tree.root.clone();
			this.size = 1;
		}

		else {
			this.root = tree.root.clone();
			this.size = tree.size;

			if (tree.root.left != null) { // if there is a left subtree
				tree.root.left.parent = null; // detach left subtree to make a
												// copy of it
				this.root.left = this.copyRec(tree.root.left); // attach copy of
																// left subtree
																// to new tree
			}

			else {
			}

			if (tree.root.right != null) { // if there is a right subtree
				tree.root.right.parent = null; // detach right subtree to make a
												// copy of it
				this.root.right = this.copyRec(tree.root.right); // attach copy
																	// of right
																	// subtree
																	// to new
																	// tree
			}

			else {
			}
		}
	}

	/**
	 * This function is here for grading purpose. It is not a good programming
	 * practice. This method is fully implemented and should not be modified.
	 * 
	 * @return root of the splay tree
	 */
	public E getRoot() {
		if (this.size == 0) {
			return null;
		}

		else
			return root.data;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Clear the splay tree.
	 */
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	// ----------
	// BST method
	// ----------

	/**
	 * Adds an element to the tree without splaying. The method carries out a
	 * binary search tree addition. It is used for initializing a splay tree.
	 * 
	 * @param data
	 * @return true if addition takes place false otherwise
	 */
	public boolean addBST(E data) {
		if (this.root == null) {
			root = new Node(data);
			++size;
			return true;
		}

		Node current = root;

		while (true) {
			int comp = current.data.compareTo(data);

			if (comp == 0) {
				return false;
			}

			else if (comp > 0) {
				if (current.left != null) {
					current = current.left;
				} else {
					Node n = new Node(data);
					current.left = n;
					n.parent = current;
					++size;
					return true;
				}
			} else {
				if (current.right != null) {
					current = current.right;
				} else {
					Node n = new Node(data);
					current.right = n;
					n.parent = current;
					++size;
					return true;
				}
			}
		}

	}

	// ------------------
	// Splay tree methods
	// ------------------

	/**
	 * Inserts an element into the splay tree. In case the element was not
	 * contained, this creates a new node and splays the tree at the new node.
	 * If the element exists in the tree already, it splays at the node
	 * containing the element.
	 * 
	 * @param data
	 *            element to be inserted
	 * @return true if addition takes place false otherwise (i.e., data is in
	 *         the tree already)
	 */
	@Override
	public boolean add(E data) {
		// DOUBLE CHECK
		if (this.root == null) {
			this.addBST(data);
			return true;
		}

		if (findEntry(data).data.compareTo(data) == 0) {
			this.splay(data);
			return false;
		}

		else {
			this.addBST(data);
			this.splay(data);
			return true;
		}
	}

	/**
	 * Determines whether the tree contains an element. Splays at the node that
	 * stores the element. If the element is not found, splays at the last node
	 * on the search path.
	 * 
	 * @param data
	 *            element to be determined whether to exist in the tree
	 * @return true if the element is contained in the tree false otherwise
	 */
	public boolean contains(E data) {
		Node n = findEntry(data);

		this.splay(n);
		this.root = n;

		if (n.data.compareTo(data) != 0) {
			return false;
		}

		else {
			return true;
		}
	}

	/**
	 * Splays at a node containing data. Exists for coding convenience, code
	 * readability, and testing purpose.
	 * 
	 * @param data
	 */
	public void splay(E data) {
		contains(data);
	}

	/**
	 * Removes the node that stores an element. Splays at its parent node after
	 * removal (No splay if the removed node was the root.) If the node was not
	 * found, the last node encountered on the search path is splayed to the
	 * root.
	 * 
	 * @param data
	 *            element to be removed from the tree
	 * @return true if the object is removed false if it was not contained in
	 *         the tree
	 */
	public boolean remove(E data) {
		Node n = findEntry(data);
		Node par = n.parent;

		if (n.data != data) {
			this.splay(n);
			return false;
		}

		else { // if n is in the tree

			if (n.data == root.data) { // if removing the root
				if (root.right == null) { // if theres no right subtree
					root = root.left;
					size--;
					return true;
				}

				else if (root.left == null) { // if theres no left subtree
					root = root.right;
					size--;
					return true;
				}

				else {
					root.left.parent = null;
					root.right.parent = null;
					this.root = join(root.left, root.right);
					size--;
					return true;

				}
			}

			else if (n.left == null && n.right == null) { // removing a leaf
				if (n == n.parent.left) { // if n is a left child
					n.parent.left = null;
					size--;
					return true;
				}

				else { // if n is a right child
					n.parent.right = null;
					size--;
					return true;
				}
			}

			else { // regular removal
				Node nL = n.left;
				Node nR = n.right;

				if (nL != null) {
					nL.parent = null;
				}

				if (nR != null) {
					nR.parent = null;
				}

				n.left = null;
				n.right = null;

				if (n == n.parent.left) { // if n is a left child
					if (nL == null) { // if theres no left subtree
						par.left = nR;

						if (nR != null) {
							nR.parent = par;
						}
					}

					else if (nR == null) { // if theres no right subtree
						par.left = nL;
						nL.parent = par;
					} else { // if joining needs to happen
						n = join(nL, nR);
						n.parent = par;
						par.left = n;
					}
				}

				else { // if n is a right child
					if (nL == null) { // if theres no left subtree
						par.right = nR;

						if (nR != null) {
							nR.parent = par;
						}
					}

					else if (nR == null) { // if theres no right subtree
						par.right = nL;
						nL.parent = par;
					}

					else { // if joining needs to happen
						n = join(nL, nR);
						n.parent = par;
						par.right = n;
					}
				}
				this.splay(n.parent);
				this.root = n.parent;
				size--;
				return true;
			}
		}
	}

	/**
	 * This method finds an element stored in the splay tree that is equal to
	 * data as decided by the compareTo() method of the class E. This is useful
	 * for retrieving the value of a pair <key, value> stored at some node
	 * knowing the key, via a call with a pair <key, ?> where ? can be any
	 * object of E.
	 * 
	 * Splays at the node containing the element or the last node on the search
	 * path.
	 * 
	 * @param data
	 * @return element such that element.compareTo(data) == 0
	 */
	public E findElement(E data) {
		if (this.findEntry(data) != null) {
			E ret = this.findEntry(data).data;

			this.splay(data);

			if (ret.compareTo(data) != 0) {
				return null;
			}

			else {
				return ret;
			}
		}

		else
			return null;
	}

	/**
	 * Finds the node that stores an element. It is called by several methods
	 * including contains(), add(), remove(), and findElement().
	 * 
	 * No splay at the found node.
	 *
	 * @param data
	 *            element to be searched for
	 * @return node if found or the last node on the search path otherwise null
	 *         if size == 0.
	 */
	protected Node findEntry(E data) {
		// DOUBLE CHECK

		if (this.size == 0) {
			return null;
		}

		Node current = root;

		while (current.right != null || current.left != null) {
			int comp = current.data.compareTo(data);

			if (comp == 0) {
				return current;
			}

			else if (comp > 0) {
				if (current.left == null) {
					return current;
				}

				else {
					current = current.left;
				}
			} else if (comp < 0) {
				if (current.right == null) {
					return current;
				}

				else {
					current = current.right;
				}
			}
		}
		return current;
	}

	/**
	 * Join the two subtrees T1 and T2 rooted at root1 and root2 into one. It is
	 * called by remove().
	 * 
	 * Precondition: All elements in T1 are less than those in T2.
	 * 
	 * Access the largest element in T1, and splay at the node to make it the
	 * root of T1. Make T2 the right subtree of T1. The method is called by
	 * remove().
	 * 
	 * @param root1
	 *            root of the subtree T1
	 * @param root2
	 *            root of the subtree T2
	 * @return the root of the joined subtree
	 */
	protected Node join(Node root1, Node root2) {
		// DOUBLE CHECK
		Node current = root1;

		while (current.right != null) {
			current = current.right; // current is now the largest element in T1
		}

		splay(current); // current is now the root of T1

		current.right = root2; // T2 is now the right subtree of T1
		return current;

	}

	/**
	 * Splay at the current node. This consists of a sequence of zig, zigZig, or
	 * zigZag operations until the current node is moved to the root of the
	 * tree.
	 * 
	 * @param current
	 *            node to splay
	 */
	protected void splay(Node current) {

		while (current.parent != null) {

			if (current.parent.parent == null) {
				zig(current);
			} else if (current == current.parent.left
					&& current.parent == current.parent.parent.left) { // zigZig
																		// if
																		// left
																		// child
																		// of
																		// left
																		// child
				zigZig(current);
			} else if (current == current.parent.right
					&& current.parent == current.parent.parent.right) { // zigZig
																		// if
																		// right
																		// child
																		// of
																		// right
																		// child
				zigZig(current);
			}

			else if (current == current.parent.left
					&& current.parent == current.parent.parent.right) { // zigZag
																		// if
																		// left
																		// child
																		// of a
																		// right
																		// child
				zigZag(current);
			}

			else if (current == current.parent.right
					&& current.parent == current.parent.parent.left) { // zigZag
																		// if
																		// right
																		// child
																		// of a
																		// left
																		// child
				zigZag(current);
			}
		}

	}

	/**
	 * This method performs the zig operation on a node. Calls leftRotate() or
	 * rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig operation on
	 */
	protected void zig(Node current) {
		// DOUBLE CHECK
		if (current == current.parent.right) { // if current is a right child,
												// rotate left
			leftRotate(current);
		} else if (current == current.parent.left) { // if current is a left
														// child, rotate right
			rightRotate(current);
		}
	}

	/**
	 * This method performs the zig-zig operation on a node. Calls leftRotate()
	 * or rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig-zig operation on
	 */
	protected void zigZig(Node current) {
		// DOUBLE CHECK
		if (current == current.parent.left
				&& current.parent == current.parent.parent.left) { // if current
																	// and
																	// current's
																	// parent
																	// are both
																	// left
																	// children
			rightRotate(current.parent);
			rightRotate(current);
		}

		else if (current == current.parent.right
				&& current.parent == current.parent.parent.right) { // if
																	// current
																	// and
																	// current's
																	// parent
																	// are both
																	// left
																	// children
			leftRotate(current.parent);
			leftRotate(current);
		}

	}

	/**
	 * This method performs the zig-zag operation on a node. Calls leftRotate()
	 * or rightRotate() or both.
	 * 
	 * @param current
	 *            node to perform the zig-zag operation on
	 */
	protected void zigZag(Node current) {
		zig(current);
		zig(current);
	}

	/**
	 * Carries out a left rotation at a node such that after the rotation its
	 * former parent becomes its left child.
	 * 
	 * @param current
	 */
	private void leftRotate(Node current) {
		if (current.parent != null) {
			if (current.parent.parent != null) { // if current has a grandparent

				if (current == current.parent.right
						&& current.parent == current.parent.parent.left) { // if
																			// current
																			// is
																			// the
																			// right
																			// child
																			// of
																			// a
																			// left
																			// child

					if (current.left != null) { // if current has left subtree
						Node leftSub = current.left;// store current's left
													// subtree

						current.left = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.left.parent = current;// updating current's
														// parents links
						current.left.right = leftSub;
						leftSub.parent = current.left; // don't forget to update
														// the subtree link

						current.parent.left = current;// link current's
														// grandparent to it
					}

					else {
						current.left = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.left.parent = current;// updating current's
														// parents links
						current.left.right = null;

						current.parent.left = current;// link current's
														// grandparent to it
					}
				} else { // current is the right child of a right child

					if (current.left != null) { // if current has left subtree
						Node leftSub = current.left;// store current's left
													// subtree

						current.left = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.left.parent = current;// updating current's
														// parents links
						current.left.right = leftSub;
						leftSub.parent = current.left; // don't forget to update
														// the subtree link

						current.parent.right = current;// link current's
														// grandparent to it
					}

					else {
						current.left = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.left.parent = current;// updating current's
														// parents links
						current.left.right = null;

						current.parent.right = current;// link current's
														// grandparent to it
					}

				}
			} else {
				if (current.left != null) { // if current has left subtree
					Node leftSub = current.left;// store current's left subtree

					current.left = current.parent; // updating current's links

					current.left.parent = current;// updating current's parents
													// links
					current.left.right = leftSub;
					leftSub.parent = current.left; // don't forget to update the
													// subtree link

					current.parent = null;// if current doesn't have a
											// grandparent, it becomes the root
				} else {
					current.left = current.parent; // updating current's links

					current.left.parent = current;// updating current's parents
													// links
					current.left.right = null;

					current.parent = null;// if current doesn't have a
											// grandparent, it becomes the root
				}

			}
		}
	}

	/**
	 * Carries out a right rotation at a node such that after the rotation its
	 * former parent becomes its right child.
	 * 
	 * @param current
	 */
	private void rightRotate(Node current) {

		if (current.parent != null) {
			if (current.parent.parent != null) {// if current has a grandparent
				if (current == current.parent.left
						&& current.parent == current.parent.parent.right) { // iff
																			// current
																			// is
																			// the
																			// left
																			// child
																			// of
																			// a
																			// right
																			// child
					if (current.right != null) { // If current has a right
													// subtree
						Node rightSub = current.right; // store current's right
														// subtree

						current.right = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.right.left = rightSub; // updating current's
														// parent's links
						rightSub.parent = current.right;// don't forget to
														// update the subtree's
														// parent link
						current.right.parent = current;

						current.parent.right = current; // link current's
														// grandparent to it
					} else {
						current.right = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.right.left = null; // updating current's
													// parent's links
						current.right.parent = current;

						current.parent.right = current; // link current's
														// grandparent to it
					}
				} else { // if current is the left child of a left child
					if (current.right != null) { // If current has a right
													// subtree
						Node rightSub = current.right; // store current's right
														// subtree

						current.right = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.right.left = rightSub; // updating current's
														// parent's links
						rightSub.parent = current.right;// don't forget to
														// update the subtree's
														// parent link
						current.right.parent = current;

						current.parent.left = current; // link current's
														// grandparent to it
					} else {
						current.right = current.parent; // updating current's
														// links
						current.parent = current.parent.parent;

						current.right.left = null; // updating current's
													// parent's links
						current.right.parent = current;

						current.parent.left = current; // link current's
														// grandparent to it
					}

				}
			} else {
				if (current.right != null) { // If current has a right subtree
					Node rightSub = current.right; // store current's right
													// subtree

					current.right = current.parent; // updating current's links

					current.right.left = rightSub; // updating current's
													// parent's links
					rightSub.parent = current.right;// don't forget to update
													// the subtree's parent link
					current.right.parent = current;

					current.parent = null;// if current doesn't have a
											// grandparent it will become the
											// root
				} else {
					current.right = current.parent; // updating current's links

					current.right.left = null; // updating current's parent's
												// links
					current.right.parent = current;

					current.parent = null;// if current doesn't have a
											// grandparent it will become the
											// root
				}

			}
		}
	}

	private Node copyRec(Node node) {
		Node copy = node.clone();

		if (node.left == null && node.right == null) { // leaf
			return copy;
		}

		if (node.left != null) {
			copy.left = copyRec(node.left);
		}

		else if (node.right != null) {
			copy.right = copyRec(node.right);
		}

		return copy;
	}

	@Override
	public Iterator<E> iterator() {
		return new SplayTreeIterator();
	}

	/**
	 * Write the splay tree according to the format specified in Section 2.2 of
	 * the project description.
	 * 
	 * Calls toStringRec().
	 *
	 */
	@Override
	public String toString() {

		sb = new StringBuilder();
		String s = toStringRec(this.root, 0);
		return s;
	}

	private String toStringRec(Node n, int depth) {

		for (int i = 0; i < depth * 4; ++i) {
			sb.append(" ");
		}

		if (n == null) // null node
		{
			sb.append("null\n");
			return sb.toString();
		}

		else if (n.left == null && n.right == null) { // leaf
			sb.append(n.data.toString());
			sb.append("\n");
			return sb.toString();
		}

		else {
			sb.append(n.data.toString());
			sb.append("\n");
		}

		if (n.left != null || n.right != null) {
			toStringRec(n.left, depth + 1);
			toStringRec(n.right, depth + 1);
		}

		return sb.toString();
	}

	/**
	 *
	 * Iterator implementation for this splay tree. The elements are returned in
	 * ascending order according to their natural ordering. All iterator methods
	 * are exactly the same as those for a binary search tree --- no splaying at
	 * any node as the cursor moves.
	 *
	 */
	private class SplayTreeIterator implements Iterator<E> {
		Node cursor;
		Node pending;

		public SplayTreeIterator() {
			cursor = root;

			if (cursor != null) {
				while (cursor.left != null) {
					cursor = cursor.left; // cursor is initialized to smallest
											// element
				}
			}
		}

		@Override
		public boolean hasNext() {
			// DOUBLE CHECK
			return cursor != null;
		}

		@Override
		public E next() {
			// DOUBLE CHECK
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			pending = cursor;
			cursor = successor(cursor);
			return pending.data;
		}

		@Override
		public void remove() {
			// DOUBLE CHECK
			if (pending == null)
				throw new IllegalStateException();

			// Remember, current points to the successor of
			// pending, but if pending has two children, then
			// unlinkNode(pending) will copy the successor's data
			// into pending and delete the successor node.
			// So in this case we want to end up with current
			// pointing to the pending node.
			if (pending.left != null && pending.right != null) {
				cursor = pending;
			}
			unlinkNode(pending);
			pending = null;
		}

		public Node successor(Node n) {
			if (n == null) {
				return null;
			} else if (n.right != null) {
				Node current = n.right;
				while (current.left != null) {
					current = current.left;
				}
				return current;
			} else {
				Node current = n.parent;
				Node child = n;
				while (current != null && current.right == child) {
					child = current;
					current = current.parent;
				}
				return current;
			}
		}

		protected void unlinkNode(Node n) {
			// first deal with the two-child case; copy
			// data from successor up to n, and then delete successor
			// node instead of given node n
			if (n.left != null && n.right != null) {
				Node s = successor(n);
				n.data = s.data;
				n = s; // causes s to be deleted in code below
			}

			// n has at most one child
			Node replacement = null;
			if (n.left != null) {
				replacement = n.left;
			} else if (n.right != null) {
				replacement = n.right;
			}

			// link replacement into tree in place of node n
			// (replacement may be null)
			if (n.parent == null) {
				root = replacement;
			} else {
				if (n == n.parent.left) {
					n.parent.left = replacement;
				} else {
					n.parent.right = replacement;
				}
			}

			if (replacement != null) {
				replacement.parent = n.parent;
			}

			--size;
		}
	}
}
