package edu.iastate.cs228.hw1;

/**
 * 
 * Living refers to the life form occupying a square in a world grid. It is a
 * superclass of Empty, Grass, and Animal, the latter of which is in turn a
 * superclass of Badger, Fox, and Rabbit. Living has two abstract methods
 * awaiting implementation.
 *
 * @author Caleb Utesch
 */
public abstract class Living {
	protected World world; // the world in which the life form resides
	protected int row; // location of the square on which
	protected int col; // the life form resides

	// constants to be used as indices.
	protected static final int BADGER = 0;
	protected static final int EMPTY = 1;
	protected static final int FOX = 2;
	protected static final int GRASS = 3;
	protected static final int RABBIT = 4;

	public static final int NUM_LIFE_FORMS = 5;

	// life expectancies
	public static final int BADGER_MAX_AGE = 4;
	public static final int FOX_MAX_AGE = 6;
	public static final int RABBIT_MAX_AGE = 3;

	public Living(World w, int r, int c) {
		world = w;
		row = r;
		col = c;
	}

	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a world.
	 * 
	 * @param population
	 *            counts of all life forms
	 */
	protected void census(int population[]) {
		// TODO
		//

		int badgers = 0;
		int foxes = 0;
		int empty = 0;
		int grass = 0;
		int rabbit = 0;

		// Neighborhood for top left corner
		if (row == 0 && col == 0) {
			for (int r = 0; r <= 1; ++r) {
				for (int c = 0; c <= 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Neighborhood for top right corner
		else if (row == 0 && col == world.getWidth() - 1) {
			for (int r = 0; r <= 1; ++r) {
				for (int c = world.getWidth() - 2; c <= world.getWidth() - 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Neighborhood for bottom left corner
		else if (row == world.getWidth() - 1 && col == 0) {
			for (int r = world.getWidth() - 2; r <= world.getWidth() - 1; ++r) {
				for (int c = 0; c <= 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Neighborhood for bottom right corner
		else if (row == world.getWidth() - 1 && col == world.getWidth() - 1) {
			for (int r = world.getWidth() - 2; r <= world.getWidth() - 1; ++r) {
				for (int c = world.getWidth() - 2; c <= world.getWidth() - 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Left edge
		else if (row >= 1 && row < world.getWidth() - 1 && col == 0) {
			for (int r = row - 1; r <= row + 1; ++r) {
				for (int c = 0; c <= 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Right edge
		else if (row >= 1 && row < world.getWidth() - 1
				&& col == world.getWidth() - 1) {
			for (int r = row - 1; r <= row + 1; ++r) {
				for (int c = world.getWidth() - 2; c <= world.getWidth() - 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Top edge
		else if (col >= 1 && col < world.getWidth() - 1 && row == 0) {
			for (int r = 0; r <= 1; ++r) {
				for (int c = col - 1; c <= col + 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// Bottom edge
		else if (col >= 1 && col < world.getWidth() - 1
				&& row == world.getWidth() - 1) {
			for (int r = world.getWidth() - 2; r <= world.getWidth() - 1; ++r) {
				for (int c = col - 1; c <= col + 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		// scans a regular 3x3 neighborhood
		// and records the numbers of living
		// objects for each type of object
		else {
			for (int r = row - 1; r <= row + 1; ++r) {
				for (int c = col - 1; c <= col + 1; ++c) {
					if (world.grid[r][c].who() == State.BADGER) {
						badgers += 1;
					} else if (world.grid[r][c].who() == State.FOX) {
						foxes += 1;
					} else if (world.grid[r][c].who() == State.EMPTY) {
						empty += 1;
					} else if (world.grid[r][c].who() == State.GRASS) {
						grass += 1;
					} else {
						rabbit += 1;
					}
				}
			}
		}

		population[BADGER] = badgers;
		population[EMPTY] = empty;
		population[FOX] = foxes;
		population[GRASS] = grass;
		population[RABBIT] = rabbit;
		// Count the numbers of Badgers, Empties, Foxes, Grasses, and Rabbits
		// in the 3 by 3 neighborhood centered at this Living object. Store the
		// counts in the array population[] at indices 0, 1, 2, 3, 4,
		// respectively.
	}

	/**
	 * Gets the identity of the life form on the square.
	 * 
	 * @return State
	 */
	public abstract State who();

	// To be implemented in each class of Badger, Empty, Fox, Grass, and Rabbit.
	//
	// There are five states given in State.java. Include the prefix State in
	// the return value, e.g., return State.Fox instead of Fox.

	/**
	 * Determines the life form on the square in the next cycle.
	 * 
	 * @param wNew
	 *            world of the next cycle
	 * @return Living
	 */
	public abstract Living next(World wNew);
	// To be implemented in the classes Badger, Empty, Fox, Grass, and Rabbit.
	//
	// For each class (life form), carry out the following:
	//
	// 1. Obtains counts of life forms in the 3X3 neighborhood of the class
	// object.

	// 2. Applies the survival rules for the life form to determine the life
	// form
	// (on the same square) in the next cycle. These rules are given in the
	// project description.
	//
	// 3. Generate this new life form at the same location in the world wNew.

}
