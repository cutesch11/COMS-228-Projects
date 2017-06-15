package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test cases for the Grass Class
 * 
 * @author Caleb Utesch
 *
 */

public class GrassTest {

	@Test
	public void testSurvivalRule1() {
		World w = new World(4);
		Grass g = new Grass(w, 2, 2);
		w.grid[1][1] = new Rabbit(w, 1, 1, 0);
		w.grid[1][2] = new Rabbit(w, 1, 2, 0);
		w.grid[1][3] = new Rabbit(w, 1, 3, 0);
		w.grid[2][1] = new Empty(w, 2, 1);
		w.grid[2][2] = g;
		w.grid[2][3] = new Empty(w, 2, 3);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Empty(w, 3, 2);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(State.EMPTY, g.next(w).who());
	}

	@Test
	public void testSurvivalRule2() {
		World w = new World(4);
		Grass g = new Grass(w, 2, 2);
		w.grid[1][1] = new Rabbit(w, 1, 1, 0);
		w.grid[1][2] = new Rabbit(w, 1, 2, 0);
		w.grid[1][3] = new Rabbit(w, 1, 3, 0);
		w.grid[2][1] = new Grass(w, 2, 1);
		w.grid[2][2] = g;
		w.grid[2][3] = new Grass(w, 2, 3);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Empty(w, 3, 2);
		w.grid[3][3] = new Fox(w, 3, 3, 0);
		assertEquals(State.RABBIT, g.next(w).who());
	}

	@Test
	public void testSurvivalRule3() {
		World w = new World(4);
		Grass g = new Grass(w, 2, 2);
		w.grid[1][1] = new Fox(w, 1, 1, 0);
		w.grid[1][2] = new Fox(w, 1, 2, 0);
		w.grid[1][3] = new Fox(w, 1, 3, 0);
		w.grid[2][1] = new Fox(w, 2, 1, 0);
		w.grid[2][2] = g;
		w.grid[2][3] = new Fox(w, 2, 3, 0);
		w.grid[3][1] = new Fox(w, 3, 1, 0);
		w.grid[3][2] = new Fox(w, 3, 2, 0);
		w.grid[3][3] = new Badger(w, 3, 3, 0);
		assertEquals(State.GRASS, g.next(w).who());
	}

}
