package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import edu.iastate.cs228.hw5.SplayTree.Node;

/**
 * 
 * @author Caleb Utesch
 *
 */

public class VideoStore {
	protected SplayTree<Video> inventory; // all the videos at the store

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor sets inventory to an empty tree.
	 */
	VideoStore() {
		inventory = new SplayTree();
	}

	/**
	 * Constructor accepts a video file to create its inventory. Refer to
	 * Section 3.2 of the project description for details regarding the format
	 * of a video file.
	 * 
	 * The construtor works in two steps:
	 * 
	 * 1. Calls the default constructor. 2. Has the splay tree inventory call
	 * its method addBST() to add videos to the tree.
	 * 
	 * @param videoFile
	 *            no format checking on the file
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	VideoStore(String videoFile) throws FileNotFoundException,
			IllegalArgumentException {
		inventory = new SplayTree();
		setUpInventory(videoFile);
	}

	/**
	 * Accepts a video file to initialize the splay tree inventory. To be
	 * efficient, add videos to the inventory by calling the addBST() method,
	 * which does not splay.
	 * 
	 * Refer to Section 3.2 for the format of video file.
	 * 
	 * @param videoFile
	 *            correctly formated if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	public void setUpInventory(String videoFile) throws FileNotFoundException,
			IllegalArgumentException {
		if (inventory != null) {
			inventory.clear();
		}
		bulkImport(videoFile);
	}

	// ------------------
	// Inventory Addition
	// ------------------

	/**
	 * Find a Video object by film title.
	 * 
	 * @param film
	 * @return
	 */
	public Video findVideo(String film) {
		return inventory.findElement(new Video(film));
	}

	/**
	 * Updates the splay tree inventory by adding a given number of video copies
	 * of the film. (Splaying is justified as new videos are more likely to be
	 * rented.)
	 * 
	 * Calls the add() method of SplayTree to add the video object. If true is
	 * returned, the film was not on the inventory before, and has been added.
	 * If false is returned, the film is already on the inventory. The root of
	 * the splay tree must store the corresponding Video object for the film.
	 * Calls findElement() of the SplayTree class to get this Video object,
	 * which then calls getNumCopies() and addNumCopies() of the Video class to
	 * increase the number of copies of the corresponding film by n
	 * 
	 * @param film
	 *            title of the film
	 * @param n
	 *            number of video copies
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 */
	public void addVideo(String film, int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		Video v = new Video(film, n);
		boolean result = inventory.add(v);

		if (result == false) {
			Video v2 = inventory.findElement(v);
			v2.addNumCopies(n);
		}
	}

	/**
	 * Add one video copy of the film.
	 * 
	 * @param film
	 *            title of the film
	 */
	public void addVideo(String film) {
		addVideo(film, 1);
	}

	/**
	 * Update the splay trees inventory.
	 * 
	 * The videoFile format is given in Section 3.2 of the project description.
	 * 
	 * @param videoFile
	 *            correctly formated if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film in videoFile is <= 0
	 */
	public void bulkImport(String videoFile) throws FileNotFoundException,
			IllegalArgumentException {
		File f = new File(videoFile);
		Scanner s = new Scanner(f);

		while (s.hasNextLine()) {
			String filmData = s.nextLine();
			String name = parseFilmName(filmData);
			int copies = parseNumCopies(filmData);

			if (copies <= 0) {
				s.close();
				throw new IllegalArgumentException();
			}

			this.inventory.addBST(new Video(name, copies));
		}
		s.close();

	}

	// ----------------------------
	// Video Query, Rental & Return
	// ----------------------------

	/**
	 * Search the splay tree inventory to determine if a video is available.
	 * 
	 * @param film
	 * @return true if available
	 */
	public boolean available(String film) {
		if (this.inventory.root == null) { // make sure the inventory has
											// something in it
			return false;
		}

		else {
			if (inventory.findElement(new Video(film)).getNumAvailableCopies() != 0) {
				return true;
			} else
				return false;
		}

	}

	/**
	 * Update inventory.
	 * 
	 * Search if the film is in inventory by calling findElement(new Video(film,
	 * 1)).
	 * 
	 * If the film is not in inventory, prints the message "Film <film> is not
	 * in inventory", where <film> shall be replaced with the string that is the
	 * value of the parameter film. If the film is in inventory with no copy
	 * left, prints the message "Film <film> has been rented out".
	 * 
	 * If there is at least one available copy but n is greater than the number
	 * of such copies, rent all available copies. In this case, no
	 * AllCopiesRentedOutException is thrown.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 * @throws FilmNotInInventoryException
	 *             if film is not in the inventory
	 * @throws AllCopiesRentedOutException
	 *             if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException,
			FilmNotInInventoryException, AllCopiesRentedOutException {
		if (n <= 0) {
			throw new IllegalArgumentException("Film " + film
					+ " has an invalid request");
		}

		if (inventory != null) { // make sure inventory isn't null

			Video v = inventory.findElement(new Video(film, 1));

			if (v == null) { // if the film isn't in the inventory
				throw new FilmNotInInventoryException("Film " + film
						+ " is not in inventory");
			}

			else if (v.getNumRentedCopies() == v.getNumCopies()) { // if the
																	// film has
																	// been
																	// rented
																	// out
				throw new AllCopiesRentedOutException("Film " + film
						+ " has been rented out");
			}

			else { // if the film is in the inventory
				if (n >= v.getNumAvailableCopies()) {
					v.rentCopies(v.getNumAvailableCopies());
				}

				else {
					v.rentCopies(n);
				}
			}

		}
	}

	/**
	 * Update inventory.
	 * 
	 * 1. Calls videoRent() repeatedly for every video listed in the file. 2.
	 * For each requested video, do the following: a) If it is not in inventory
	 * or is rented out, an exception will be thrown from rent(). Based on the
	 * exception, prints out the following message:
	 * "Film <film> is not in inventory" or "Film <film> has been rented out."
	 * In the message, <film> shall be replaced with the name of the video. b)
	 * Otherwise, update the video record in the inventory.
	 * 
	 * @param videoFile
	 *            correctly formatted if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException
	 *             if any film from the videoFile is not in the inventory
	 * @throws AllCopiesRentedOutException
	 *             if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException,
			IllegalArgumentException, FilmNotInInventoryException,
			AllCopiesRentedOutException {
		File f = new File(videoFile);
		Scanner s = new Scanner(f);
		String t = "";
		boolean[] bs = new boolean[3];

		while (s.hasNextLine()) {
			String videoData = s.nextLine();

			Video v = new Video(parseFilmName(videoData),
					parseNumCopies(videoData));

			try {
				videoRent(v.getFilm(), v.getNumCopies());
			} catch (IllegalArgumentException e) {
				t += e.getMessage() + "\n";
				bs[0] = true;

			} catch (FilmNotInInventoryException e) {
				t += e.getMessage() + "\n";
				bs[1] = true;
			} catch (AllCopiesRentedOutException e) {
				t += e.getMessage() + "\n";
				bs[2] = true;
			}
		}
		if (bs[0]) {
			s.close();
			throw new IllegalArgumentException(t.trim());
		}

		if (bs[1]) {
			s.close();
			throw new FilmNotInInventoryException(t.trim());
		}
		if (bs[2]) {
			s.close();
			throw new AllCopiesRentedOutException(t.trim());
		}
		s.close();
	}

	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number
	 * of rented copies while ignoring the extra copies.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 * @throws FilmNotInInventoryException
	 *             if film is not in the inventory
	 */
	public void videoReturn(String film, int n)
			throws IllegalArgumentException, FilmNotInInventoryException {
		if (n <= 0) {
			throw new IllegalArgumentException("Film " + film
					+ " has an invalid request");
		}

		Video v = inventory.findElement(new Video(film, n));

		if (v == null) {
			throw new FilmNotInInventoryException("Film " + film
					+ " is not in inventory");
		}

		else {
			if (n > v.getNumRentedCopies()) {
				v.returnCopies(v.getNumCopies());
			}

			else {
				v.returnCopies(n);
			}
		}

	}

	/**
	 * Update inventory.
	 * 
	 * Handles excessive returned copies of a film in the same way as
	 * videoReturn() does.
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 *             if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException
	 *             if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException,
			IllegalArgumentException, FilmNotInInventoryException {
		File f = new File(videoFile);
		Scanner s = new Scanner(f);
		String t = "";
		boolean[] bs = new boolean[2];

		while (s.hasNextLine()) {
			String videoData = s.nextLine();

			Video v = new Video(parseFilmName(videoData),
					parseNumCopies(videoData));

			try {
				videoReturn(v.getFilm(), v.getNumCopies());
			} catch (IllegalArgumentException e) {
				t += e.getMessage() + "\n";
				bs[0] = true;

			} catch (FilmNotInInventoryException e) {
				t += e.getMessage() + "\n";
				bs[1] = true;
			}
		}

		if (bs[0]) {
			s.close();
			throw new IllegalArgumentException(t.trim());
		}

		if (bs[1]) {
			s.close();
			throw new FilmNotInInventoryException(t.trim());
		}
		s.close();

	}

	// ------------------------
	// Methods with No Splaying
	// ------------------------

	/**
	 * Performs inorder traveral on the splay tree inventory to list all the
	 * videos by film title, whether rented or not. Below is a sample string if
	 * printed out:
	 * 
	 * 
	 * Films in inventory:
	 * 
	 * A Streetcar Named Desire (1) Brokeback Mountain (1) Forrest Gump (1)
	 * Psycho (1) Singin' in the Rain (2) Slumdog Millionaire (5) Taxi Driver
	 * (1) The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	public String inventoryList() {
		Iterator<Video> iter = inventory.iterator();
		Node current = inventory.root;
		String vids = "";

		while (current.left != null) { // finds lowest element specified by
										// natural order
			current = current.left;
		}

		vids += "Films in inventory:\n\n";

		while (iter.hasNext()) {
			Video vid = iter.next();
			String name = vid.getFilm();
			int cops = vid.getNumCopies();
			vids += name + " (" + cops + ")\n";
		}
		return vids;
	}

	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially. For the
	 * string format, see Transaction 5 in the sample simulation in Section 4 of
	 * the project description.
	 * 
	 * @return
	 */
	public String transactionsSummary() {
		String s = rentedVideosList();
		s += "\n";
		s += unrentedVideosList();
		return s;
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Rented films:
	 * 
	 * Brokeback Mountain (1) Singin' in the Rain (2) Slumdog Millionaire (1)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList() {
		Iterator<Video> iter = inventory.iterator();
		Node current = inventory.root;
		String vids = "";

		while (current.left != null) { // finds lowest element specified by
										// natural order
			current = current.left;
		}

		vids += "Rented films:\n\n";

		while (iter.hasNext()) {
			Video vid = iter.next();
			if (vid.getNumRentedCopies() != 0) { // only add to list if the film
													// has copies rented out
				String name = vid.getFilm();
				int cops = vid.getNumRentedCopies();
				vids += name + " (" + cops + ")\n";
			}
		}
		return vids;
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator. Prints only the films that have unrented copies.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) Forrest Gump (1) Psycho (1) Slumdog
	 * Millionaire (4) Taxi Driver (1)
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList() {
		Iterator<Video> iter = inventory.iterator();
		Node current = inventory.root;
		String vids = "";

		while (current.left != null) { // finds lowest element specified by
										// natural order
			current = current.left;
		}

		vids += "Films remaining in inventory:\n\n";

		while (iter.hasNext()) {
			Video vid = iter.next();
			if (vid.getNumAvailableCopies() != 0) { // only add to list if the
													// film has copies left in
													// store
				String name = vid.getFilm();
				int cops = vid.getNumAvailableCopies();
				vids += name + " (" + cops + ")\n";
			}
		}
		return vids;

	}

	/**
	 * Parse the film name from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) {
		Scanner s = new Scanner(line);
		String name = "";
		while (s.hasNext()) {
			String word = s.next();
			if (word.charAt(0) == '(' && word.charAt(1) != '(' && !s.hasNext()) {
			}

			else {
				name += word;
				name += " ";
			}
		}

		s.close();
		name = name.trim();
		return name;
	}

	/**
	 * Parse the number of copies from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) {
		Scanner s = new Scanner(line);

		while (s.hasNext()) {
			String word = s.next();
			String nums = "";
			int i = 1;
			char[] chars = word.toCharArray();
			if (word.charAt(0) == '(' && word.charAt(1) != '(' && !s.hasNext()) {
				while (chars[i] != ')') {
					nums += chars[i];
					++i;
				}
				s.close();
				return Integer.parseInt(nums);
			}

			else {
			}

		}
		s.close();
		return 1;
	}
}
