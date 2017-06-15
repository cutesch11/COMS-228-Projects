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
 * This class implements selection sort.
 *
 */

public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	private long beforeTime, afterTime;

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
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "SelectionSorter";
		outputFileName = "select.txt";
	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 */
	public SelectionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		algorithm = "SelectionSorter";
		outputFileName = "select.txt";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
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

		for (int i = 0; i < points.length - 1; ++i) {
			int minIndex = i;
			for (int j = i + 1; j < points.length; ++j) {
				if (order == 1) {
					if (points[j].compareTo(points[minIndex]) == -1) {
						minIndex = j;
					}

				} else {
					if (this.pointComparator.compare(points[j],
							points[minIndex]) == -1) {
						minIndex = j;
					}
				}
			}
			this.swap(minIndex, i);
		}
		afterTime = System.nanoTime();
		sortingTime = afterTime - beforeTime;
	}
}
