package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Caleb Utesch

 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store.
 *
 */
public class Transactions {

	/**
	 * The main method generates a simulation of rental and return activities.
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IllegalArgumentException {
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the
		// the project description.
		Scanner s = new Scanner(System.in);
		VideoStore v = new VideoStore("videoList1.txt");

		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent)    2 (bulk rent)");
		System.out.println("      3 (return)  4 (bulk return)");
		System.out.println("      5 (summary) 6 (exit)");
		System.out.println();

		System.out.println("Transaction:");
		int choice = s.nextInt();

		while (choice != 6) {

			if (choice == 1) {
				System.out.println("Film to rent:");
				s.nextLine();
				String film = s.nextLine();
				Video vid = new Video(v.parseFilmName(film),
						v.parseNumCopies(film));
				try {
					v.videoRent(vid.getFilm(), vid.getNumCopies());
				}

				catch (IllegalArgumentException | FilmNotInInventoryException
						| AllCopiesRentedOutException e) {
					System.out.println(e.getMessage());
				}
			}

			if (choice == 2) {
				System.out.println("Video file (rent):");
				String file = s.next();
				try {
					v.bulkRent(file);
				}

				catch (IllegalArgumentException | FilmNotInInventoryException
						| AllCopiesRentedOutException e) {
					System.out.println(e.getMessage());
				}
			}

			if (choice == 3) {
				System.out.println("Film to return:");
				s.nextLine();
				String film = s.nextLine();
				Video vid = new Video(v.parseFilmName(film),
						v.parseNumCopies(film));
				try {
					v.videoReturn(vid.getFilm(), vid.getNumCopies());
				} catch (IllegalArgumentException | FilmNotInInventoryException e) {
					System.out.println(e.getMessage());
				}
			}

			if (choice == 4) {
				System.out.println("Video file (return):");
				String file = s.next();
				try {
					v.bulkReturn(file);
				}

				catch (IllegalArgumentException | FilmNotInInventoryException e) {
					System.out.println(e.getMessage());
				}
			}

			if (choice == 5) {
				System.out.println();
				System.out.println(v.transactionsSummary());
			}

			System.out.println();
			System.out.println("Transaction:");
			choice = s.nextInt();

		}
		s.close();
	}
}
