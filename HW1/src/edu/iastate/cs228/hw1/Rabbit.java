package edu.iastate.cs228.hw1;

/**
 * A rabbit eats grass and lives no more than three years.
 * 
 * @author Caleb Utesch
 */
public class Rabbit extends Animal {
	/**
	 * Creates a Rabbit object.
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
	public Rabbit(World w, int r, int c, int a) {
		super(w, r, c, a);
	}

	// Rabbit occupies the square.
	public State who() {
		return State.RABBIT;
	}

	/**
	 * A rabbit dies of old age or hunger, or it is eaten if there are as many
	 * foxes and badgers in the neighborhood.
	 * 
	 * @param wNew
	 *            world of the next cycle
	 * @return Living new life form occupying the same square
	 */
	public Living next(World wNew) {

		int[] population = new int[NUM_LIFE_FORMS];

		// Records the information for the neighborhood
		census(population);

		// Applies rabbit survival rules
		if (age == RABBIT_MAX_AGE) {
			return new Empty(wNew, this.row, this.col);
		}

		else if (population[GRASS] == 0) {
			return new Empty(wNew, this.row, this.col);
		}

		else if ((population[FOX] + population[BADGER]) >= population[RABBIT]
				&& population[BADGER] < population[FOX]) {
			return new Fox(wNew, this.row, this.col, 0);
		}

		else if (population[BADGER] > population[RABBIT]) {
			return new Badger(wNew, this.row, this.col, 0);
		}

		else {
			return new Rabbit(wNew, this.row, this.col, age + 1);
		}
	}
}
