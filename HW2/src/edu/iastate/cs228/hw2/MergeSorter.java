package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Caleb Utesch

 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	private long beforeTime, afterTime;

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor accepts an input array of points. in the array.
	 * 
	 * @param pts
	 *            input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "MergeSorter";
		outputFileName = "merge.txt";
	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 */
	public MergeSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		algorithm = "MergeSorter";
		outputFileName = "merge.txt";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle
	 *
	 */
	@Override
	public void sort(int order) {
		// TODO
		beforeTime = System.nanoTime();
		setComparator(order);
		this.mergeSortRec(points);
		afterTime = System.nanoTime();
		sortingTime = afterTime - beforeTime;
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[]
	 * of points. One way is to make copies of the two halves of pts[],
	 * recursively call mergeSort on them, and merge the two sorted subarrays
	 * into pts[].
	 * 
	 * @param pts
	 *            point array
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		if (n == 1) {
			return;
		}
		int m = n / 2;
		Point[] Left = new Point[m];
		Point[] Right = new Point[n - m];
		for (int i = 0; i < m; ++i) {
			Left[i] = pts[i];
		}
		int x = 0;
		for (int j = m; j < n; ++j) {
			Right[x] = pts[j];
			++x;
		}

		mergeSortRec(Left);
		mergeSortRec(Right);

		Point[] tempHold = merge(Left, Right); // saves lots of recursion calls
		for (int y = 0; y < pts.length; ++y) {
			pts[y] = tempHold[y];
		}
	}

	private Point[] merge(Point[] left, Point[] right) {
		Point[] result = new Point[left.length + right.length];
		int indexL = 0;
		int indexR = 0;
		int indexRes = 0;

		if (this.sortByAngle == false) {
			while (indexL < left.length || indexR < right.length) {
				if (indexL < left.length && indexR < right.length) {
					if (left[indexL].compareTo(right[indexR]) == -1
							|| left[indexL].compareTo(right[indexR]) == 0) {
						result[indexRes] = left[indexL];
						indexL++;
						indexRes++;
					} else {
						result[indexRes] = right[indexR];
						indexR++;
						indexRes++;
					}
				} else if (indexL < left.length) {
					result[indexRes] = left[indexL];
					++indexL;
					++indexRes;
				}

				else {
					result[indexRes] = right[indexR];
					++indexR;
					++indexRes;
				}
			}
			return result;
		} else {
			while (indexL < left.length || indexR < right.length) {
				if (indexL < left.length && indexR < right.length) {
					if (this.pointComparator.compare(left[indexL],
							right[indexR]) == -1
							|| this.pointComparator.compare(left[indexL],
									right[indexR]) == 0) {
						result[indexRes] = left[indexL];
						indexL++;
						indexRes++;
					} else {
						result[indexRes] = right[indexR];
						indexR++;
						indexRes++;
					}
				} else if (indexL < left.length) {
					result[indexRes] = left[indexL];
					indexL++;
					indexRes++;
				}

				else if (indexR < right.length) {
					result[indexRes] = right[indexR];
					indexR++;
					indexRes++;

				}
			}

		}
		return result;
	}

	// Other private methods in case you need ...

}
