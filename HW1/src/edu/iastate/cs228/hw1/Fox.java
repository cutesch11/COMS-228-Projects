package edu.iastate.cs228.hw1;

/**
 * A fox eats rabbits and competes against a badger.
 * 
 * @author Caleb Utesch
 */
public class Fox extends Animal {
	/**
	 * Constructor
	 * 
	 * @param w
	 *            : world
	 * @param r
	 *            : row position
	 * @param c
	 *            : column position
	 * @param a
	 *            : age
	 */
	public Fox(World w, int r, int c, int a) {
		super(w, r, c, a);
	}

	/**
	 * A fox occupies the square.
	 */
	public State who() {
		return State.FOX;
	}

	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior
	 * badgers.
	 * 
	 * @param wNew
	 *            world of the next cycle
	 * @return Living life form occupying the square in the next cycle.
	 */
	public Living next(World wNew) {

		int[] population = new int[NUM_LIFE_FORMS];

		// Records current neighborhood occupants
		census(population);

		// Applies Fox survival rules
		if (age == FOX_MAX_AGE) {
			return new Empty(wNew, this.row, this.col);
		}

		else if (population[BADGER] > population[FOX]) {
			return new Badger(wNew, this.row, this.col, 0);
		}

		else if ((population[BADGER] + population[FOX]) > population[RABBIT]) {
			return new Empty(wNew, this.row, this.col);
		}

		else {
			return new Fox(wNew, this.row, this.col, age + 1);
		}

	}
}
