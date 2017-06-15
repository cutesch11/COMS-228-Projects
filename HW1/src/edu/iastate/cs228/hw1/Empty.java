package edu.iastate.cs228.hw1;

/**
 * Empty squares are competed by various forms of life.
 * 
 * @author Caleb Utesch
 */
public class Empty extends Living {
	public Empty(World w, int r, int c) {
		super(w, r, c);
	}

	public State who() {
		return State.EMPTY;
	}

	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or
	 * Grass, or remain empty.
	 * 
	 * @param wNew
	 *            world of the next life cycle.
	 * @return Living life form in the next cycle.
	 */
	public Living next(World wNew) {
		int[] population = new int[NUM_LIFE_FORMS];

		// Records the current neighborhood inhabitants
		census(population);

		// Applies empty survival rules
		if (population[RABBIT] > 1) {
			return new Rabbit(wNew, this.row, this.col, 0);
		}

		else if (population[FOX] > 1) {
			return new Fox(wNew, this.row, this.col, 0);
		}

		else if (population[BADGER] > 1) {
			return new Badger(wNew, this.row, this.col, 0);
		}

		else if (population[GRASS] >= 1) {
			return new Grass(wNew, this.row, this.col);
		}

		else {
			return new Empty(wNew, this.row, this.col);
		}
	}
}
