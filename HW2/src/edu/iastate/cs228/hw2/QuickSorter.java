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
 * This class implements the version of the quicksort algorithm presented in the
 * lecture.
 *
 */

public class QuickSorter extends AbstractSorter {

	// Other private instance variables if you need ...
	private long beforeTime, afterTime;
	private Point[] pointsClone;

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor accepts an input array of points.
	 * 
	 * @param pts
	 *            input array of integers
	 */
	public QuickSorter(Point[] pts) {
		// TODO
		super(pts);
		pointsClone = new Point[points.length];
		algorithm = "QuickSorter";
		outputFileName = "quick.txt";
	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 */
	public QuickSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		algorithm = "QuickSorter";
		outputFileName = "quick.txt";
	}

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.
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
		this.quickSortRec(0, points.length - 1);
		afterTime = System.nanoTime();
		sortingTime = afterTime - beforeTime;
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 *            starting index of the subarray
	 * @param last
	 *            ending index of the subarray
	 */
	private void quickSortRec(int first, int last) {
		if (first >= last) {
			return;
		} else {
			int p = this.partition(first, last);
			quickSortRec(first, p - 1);
			quickSortRec(p + 1, last);
		}
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last) {
		// Use last element as pivot
		Point pivot = points[last];
		int i = first - 1;

		if (sortByAngle == false) {
			for (int j = first; j <= last - 1; ++j) {
				if (points[j].compareTo(pivot) == -1
						|| points[j].compareTo(pivot) == 0) {
					i++;
					swap(i, j);
				}
			}
			swap(i + 1, last);
			return i + 1;
		}

		else {
			for (int j = first; j <= last - 1; ++j) {
				if (this.pointComparator.compare(points[j], pivot) == -1
						|| this.pointComparator.compare(points[j], pivot) == 0) {
					i++;
					swap(i, j);
				}
			}
			swap(i + 1, last);
			return i + 1;

		}
	}

	// Other private methods in case you need ...
}
