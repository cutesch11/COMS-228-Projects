package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * The PredatorPrey class performs the predator-prey simulation over a grid
 * world with squares occupied by badgers, foxes, rabbits, grass, or none.
 *
 * @author Caleb Utesch
 */
public class PredatorPrey {
	/**
	 * Update the new world from the old world in one cycle.
	 * 
	 * @param wOld
	 *            old world
	 * @param wNew
	 *            new world
	 */
	public static void updateWorld(World wOld, World wNew) {

		for (int r = 0; r <= wOld.getWidth() - 1; ++r) {
			for (int c = 0; c <= wOld.getWidth() - 1; ++c) {
				wNew.grid[r][c] = wOld.grid[r][c].next(wNew);
			}
		}

		// For every life form (i.e., a Living object) in the grid wOld,
		// generate
		// a Living object in the grid wNew at the corresponding location such
		// that
		// the former life form changes into the latter life form.
		//
		// Employ the method next() of the Living class.
	}

	/**
	 * Repeatedly generates worlds either randomly or from reading files. Over
	 * each world, carries out an input number of cycles of evolution.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO
		//
		// Generate predator-prey simulations repeatedly like shown in the
		// sample run in the project description.
		// System.out.println("The Predator-Prey Simulator");
		System.out.println("keys: 1 (random world) 2 (file input) 3 (exit)");
		System.out.println("Enter world creation option:");
		Scanner s = new Scanner(System.in);
		int wCreate = s.nextInt();
		int tNumber = 1;
		while (wCreate == 1 || wCreate == 2) {
			System.out.println("Trial " + tNumber + ":");
			if (wCreate == 1) {
				System.out.println("Random World");
				System.out.println("Enter Grid Width:");
				int gWidth = s.nextInt();
				System.out.println("Enter number of cycles:");
				int cycles = s.nextInt();
				if (cycles == 0 || cycles < 0) {
					while (cycles == 0 || cycles < 0) {
						System.out.println("Enter number of cycles:");
						cycles = s.nextInt();
					}
				}
				World even = new World(gWidth);
				World odd = new World(gWidth);
				even.randomInit();
				System.out.println("Initial World:");
				System.out.println(even.toString());

				for (int x = 0; x <= cycles; ++x) {
					if (x % 2 == 0) {
						updateWorld(even, odd);
					} else {
						updateWorld(odd, even);
					}

				}
				if (cycles % 2 == 0) {
					System.out.println("Final World:");
					System.out.println(even.toString());
				} else {
					System.out.println("Final World:");
					System.out.println(odd.toString());
				}
			}

			else if (wCreate == 2) {
				System.out.println("World input from a file");
				System.out.println("File name:");
				String FileName = s.next();
				System.out.println("Enter the number of cycles:");
				int cycles = s.nextInt();
				if (cycles == 0 || cycles < 0) {
					while (cycles == 0 || cycles < 0) {
						System.out.println("Enter number of cycles:");
						cycles = s.nextInt();
					}
				}
				World even = new World(FileName);
				World odd = new World(even.getWidth());
				System.out.println("Inital World");
				System.out.println(even.toString());

				for (int x = 0; x <= cycles; ++x) {
					if (x % 2 == 0) {
						updateWorld(even, odd);
					} else {
						updateWorld(odd, even);
					}
				}

				if (cycles % 2 == 0) {
					System.out.println("Final World");
					System.out.println(even.toString());

				}

				else {
					System.out.println("Final World");
					System.out.println(odd.toString());
				}

			}

			else {
				return;
			}
			tNumber += 1;
			System.out.println("Enter world creation option:");
			wCreate = s.nextInt();
		}
	}

	// 1. Enter 1 to generate a random world, 2 to read a world from an input
	// file, and 3 to end the simulation. (An input file always ends with
	// the suffix .txt.)
	//
	// 2. Print out standard messages as given in the project description.
	//
	// 3. For convenience, you may define two worlds even and odd as below.
	// In an even numbered cycle (starting at zero), generate the world
	// odd from the world even; in an odd numbered cycle, generate even
	// from odd.

	// World even = new World(1); // the world after an even number of cycles
	// World odd; // the world after an odd number of cycles

	// 4. Print out initial and final worlds only. No intermediate worlds should
	// appear in the standard output. (When debugging your program, you can
	// print intermediate worlds.)
	//
	// 5. You may save some randomly generated worlds as your own test cases.
	//
	// 6. It is not necessary to handle file input & output exceptions for this
	// project. Assume data in an input file to be correctly formated.

}
