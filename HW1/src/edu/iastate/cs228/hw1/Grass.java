package edu.iastate.cs228.hw1;

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is
 * eaten.
 *
 * @author Caleb Utesch
 */
public class Grass extends Living {
	public Grass(World w, int r, int c) {
		super(w, r, c);
	}

	public State who() {
		return State.GRASS;
	}

	/**
	 * Grass can be eaten out by too many rabbits in the neighborhood. Rabbits
	 * may also multiply fast enough to take over Grass.
	 */
	public Living next(World wNew) {
		int[] population = new int[NUM_LIFE_FORMS];

		// Records current neighborhood inhabitants
		census(population);

		// Applies grass survival rules

		if (population[RABBIT] >= (population[GRASS] * 3)) {
			return new Empty(wNew, this.row, this.col);
		}

		else if (population[RABBIT] >= 3) {
			return new Rabbit(wNew, this.row, this.col, 0);
		}

		else {
			return new Grass(wNew, this.row, this.col);
		}
	}
}
