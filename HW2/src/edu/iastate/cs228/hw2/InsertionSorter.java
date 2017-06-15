package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Caleb Utesch
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	private long afterTime, beforeTime;

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = "InsertionSorter";
		outputFileName = "insert.txt";
	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 */
	public InsertionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		algorithm = "InsertionSorter";
		outputFileName = "insert.txt";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle
	 */
	@Override
	public void sort(int order) {
		// TODO
		beforeTime = System.nanoTime();
		setComparator(order);

		for (int i = 1; i < points.length; ++i) {
			Point key = points[i];
			int j = i - 1;

			if (order == 1) {
				while (j >= 0 && points[j].compareTo(key) == 1) {
					points[j + 1] = points[j];
					j--;
				}
				points[j + 1] = key;
			} else {
				while (j >= 0
						&& (this.pointComparator.compare(points[j], key) == 1)) {
					points[j + 1] = points[j];
					j--;
				}
				points[j + 1] = key;

			}
		}
		afterTime = System.nanoTime();
		sortingTime = afterTime - beforeTime;
	}
}
