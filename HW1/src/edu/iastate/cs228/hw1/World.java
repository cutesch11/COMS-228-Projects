package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * The world is represented as a square grid of size width X width.
 *
 * @author Caleb Utesch
 */
public class World {
	private int width; // grid size: width X width

	public Living[][] grid;

	/**
	 * Default constructor reads from a file
	 */
	public World(String inputFileName) throws FileNotFoundException {
		// CHECK OVER
		File f = new File(inputFileName);
		Scanner s = new Scanner(f);
		width = 0;

		// Reads the first line of the file
		// to determine the width of the grid
		while (s.hasNextLine() == true) {
			width++;
			s.nextLine();
		}
		s.close();

		grid = new Living[width][width];

		// Stores the data from the file
		// into a newly established grid
		Scanner t = new Scanner(f);
		for (int r = 0; r <= width - 1; ++r) {
			for (int c = 0; c <= width - 1; ++c) {
				String Liv = t.next(); // Stores the current string being
										// scanned

				if (Liv.charAt(0) == 'B') {
					grid[r][c] = new Badger(this, r, c,
							Character.getNumericValue(Liv.charAt(1)));
				}

				else if (Liv.charAt(0) == 'F') {
					grid[r][c] = new Fox(this, r, c,
							Character.getNumericValue(Liv.charAt(1)));
				}

				else if (Liv.charAt(0) == 'R') {
					grid[r][c] = new Rabbit(this, r, c,
							Character.getNumericValue(Liv.charAt(1)));
				}

				else if (Liv.charAt(0) == 'E') {
					grid[r][c] = new Empty(this, r, c);
				}

				else {
					grid[r][c] = new Grass(this, r, c);
				}
			}
			t.nextLine();
		}
		t.close();
		// remember to close file

		// Assumption: The input file is in correct format.
		//
		// You may create the grid world in the following steps:
		//
		// 1) Reads the first line to determine the width of the grid.
		//
		// 2) Creates a grid object.
		//
		// 3) Fills in the grid according to the input file.
		//
		// Be sure to close the input file when you are done.
	}

	/**
	 * Constructor that builds a w X w grid without initializing it.
	 * 
	 * @param width
	 *            the grid
	 */
	public World(int w) {
		// TODO
		width = w;
		grid = new Living[w][w];
	}

	public int getWidth() {
		return width;
	}

	/**
	 * Initialize the world by randomly assigning to every square of the grid
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit() {
		// JUNIT
		Random generator = new Random();
		grid = new Living[width][width];
		// Assigns a random living object to
		// each grid square in the world object
		for (int r = 0; r <= width - 1; r++) {
			for (int c = 0; c <= width - 1; c++) {
				int param = generator.nextInt(5);
				if (param == 0) {
					grid[r][c] = new Badger(this, r, c, 0);
				} else if (param == 1) {
					grid[r][c] = new Empty(this, r, c);
				} else if (param == 2) {
					grid[r][c] = new Fox(this, r, c, 0);
				} else if (param == 3) {
					grid[r][c] = new Grass(this, r, c);
				} else if (param == 4) {
					grid[r][c] = new Rabbit(this, r, c, 0);
				}
			}
		}

	}

	/**
	 * Output the world grid. For each square, output the first letter of the
	 * living form occupying the square. If the living form is an animal, then
	 * output the age of the animal followed by a blank space; otherwise, output
	 * two blanks.
	 */
	public String toString() {
		// CHECK/TEST
		String Stgrid = "";
		for (int r = 0; r <= width - 1; ++r) {
			for (int c = 0; c <= width - 1; ++c) {
				if (grid[r][c].who() == State.BADGER) {
					int age = ((Badger) grid[r][c]).myAge(); // Downcasts current living objects to retrieve age
					Stgrid += "B" + age + " ";
				} else if (grid[r][c].who() == State.FOX) {
					int age = ((Fox) grid[r][c]).myAge();
					Stgrid += "F" + age + " ";
				} else if (grid[r][c].who() == State.RABBIT) {
					int age = ((Rabbit) grid[r][c]).myAge();
					Stgrid += "R" + age + " ";
				} else if (grid[r][c].who() == State.GRASS) {
					Stgrid += "G  ";
				} else if (grid[r][c].who() == State.EMPTY) {
					Stgrid += "E  ";
				}
			}
			if (width > 1) {
				Stgrid += '\n'; // Starts a new line of the grid
			} else {

			}
		}
		return Stgrid;
	}

	/**
	 * Write the world grid to an output file. Also useful for saving a randomly
	 * generated world for debugging purpose.
	 * 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException {
		// TEST
		File f = new File(outputFileName);
		PrintWriter writer = new PrintWriter(f);

		// Prints the current world to a file
		// Line by line
		for (int r = 0; r <= width - 1; ++r) {
			for (int c = 0; c <= width - 1; ++c) {
				if (grid[r][c].who() == State.BADGER) {
					int age = ((Badger) grid[r][c]).myAge(); // Downcasts current living object to retrieve age
					writer.print("B" + age + " ");
				} else if (grid[r][c].who() == State.FOX) {
					int age = ((Fox) grid[r][c]).myAge();
					writer.print("F" + age + " ");
				} else if (grid[r][c].who() == State.RABBIT) {
					int age = ((Rabbit) grid[r][c]).myAge();
					writer.print("R" + age + " ");
				} else if (grid[r][c].who() == State.GRASS) {
					writer.print("G ");
				} else if (grid[r][c].who() == State.EMPTY) {
					writer.print("E ");
				}
			}
			writer.println(); // Starts a new line in the output file
		}
		writer.close();

		// 1. Open the file.
		//
		// 2. Write to the file. The five life forms are represented by
		// characters
		// B, E, F, G, R. Leave one blank space in between. Examples are given
		// in
		// the project description.
		//
		// 3. Close the file.
	}

}
