package edu.iastate.cs228.hw2;

/**
 *  
 * @author Caleb Utesch
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {

	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Perform the four sorting algorithms over each sequence of
	 * integers, comparing points by x-coordinate or by polar angle with respect
	 * to the lowest point.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 **/
	public static void main(String[] args) throws InputMismatchException,FileNotFoundException {
		//
		// Conducts multiple sorting rounds. In each round, performs the
		// following:
		//
		// a) If asked to sort random points, calls generateRandomPoints() to
		// initialize an array
		// of random points.
		// b) Reassigns to elements in the array sorters[] (declared below) the
		// references to the
		// four newly created objects of SelectionSort, InsertionSort, MergeSort
		// and QuickSort.
		// c) Based on the input point order, carries out the four sorting
		// algorithms in a for
		// loop that iterates over the array sorters[], to sort the randomly
		// generated points
		// or points from an input file.
		// d) Meanwhile, prints out the table of runtime statistics.
		//
		// A sample scenario is given in Section 2 of the project description.
		//
		System.out
				.println("keys:  1 (random integers)  2 (file input)  3 (exit)");
		Scanner s = new Scanner(System.in);
		int ptGen = s.nextInt();
		System.out.println("order: 1 (by x-coordinate)  2 (by polar angle)");
		int ord;
		int tNumber = 1;
		System.out.println("Trial " + tNumber + ": " + ptGen);

		while (ptGen == 1 || ptGen == 2) {
			AbstractSorter[] sorters = new AbstractSorter[4];
			if (ptGen == 1) {
				System.out.println("Enter number of random points:");
				int rands = s.nextInt();
				System.out.println("Order Used in Sorting:");
				ord = s.nextInt();
				System.out.println();
				System.out.println();
				System.out.println("algorithm        size     time (ns)");
				System.out.println("------------------------------------");
				System.out.println();
				Point[] inputs = new Point[rands];
				inputs = generateRandomPoints(rands, new Random());
				sorters[0] = new SelectionSorter(inputs);
				sorters[1] = new InsertionSorter(inputs);
				sorters[2] = new MergeSorter(inputs);
				sorters[3] = new QuickSorter(inputs);

				for (int i = 0; i < sorters.length; ++i) {
					if (ord == 1) {
						sorters[i].sort(1);
						System.out.println(sorters[i].stats());
						sorters[i].writePointsToFile();
					} else {
						sorters[i].sort(0);
						System.out.println(sorters[i].stats());
						sorters[i].writePointsToFile();
					}
				}
				System.out.println("------------------------------------");
				System.out.println();
			}

			else {
				System.out.println("Points from a file");
				System.out.println("File name: ");
				String filename = s.next();
				System.out.println("Order Used in Sorting:");
				ord = s.nextInt();
				System.out.println();
				System.out.println();
				System.out.println("algorithm        size     time (ns)");
				System.out.println("------------------------------------");
				System.out.println();
				sorters[0] = new SelectionSorter(filename);
				sorters[1] = new InsertionSorter(filename);
				sorters[2] = new MergeSorter(filename);
				sorters[3] = new QuickSorter(filename);

				for (int i = 0; i < sorters.length; ++i) {
					if (ord == 1) {
						sorters[i].sort(1);
						System.out.println(sorters[i].stats());
						sorters[i].writePointsToFile();
					} else {
						sorters[i].sort(0);
						System.out.println(sorters[i].stats());
						sorters[i].writePointsToFile();
					}
				}
				System.out.println("------------------------------------");
				System.out.println();

			}
			tNumber += 1;
			System.out.println("Trial " + tNumber + ":");
			ptGen = s.nextInt();
		}
		s.close();

		// Within a sorting round, every sorter object write its output to the
		// file
		// "select.txt", "insert.txt", "merge.txt", or "quick.txt" if it is an
		// object of
		// SelectionSort, InsertionSort, MergeSort, or QuickSort, respectively.

	}

	/**
	 * This method generates a given number of random points to initialize
	 * randomPoints[]. The coordinates of these points are pseudo-random numbers
	 * within the range [-50,50] × [-50,50]. Please refer to Section 3 on how
	 * such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts
	 *            number of points
	 * @param rand
	 *            Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException
	 *             if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand)
			throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}

		Point randomPts[] = new Point[numPts];
		Random generator = new Random();

		for (int i = 0; i < numPts; ++i) {
			randomPts[i] = new Point(generator.nextInt(101) - 50,
					generator.nextInt(101) - 50);
		}

		return randomPts;
	}
}
