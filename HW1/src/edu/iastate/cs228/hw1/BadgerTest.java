package edu.iastate.cs228.hw1;

import edu.iastate.cs228.hw1.Badger;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test cases for the Badger Class
 * 
 * @author Caleb Utesch
 *
 */

public class BadgerTest {

	@Test
	public void testAge() {
		World w = new World(5);
		Badger b = new Badger(w, 0, 0, 0);
		assertEquals(0, b.myAge());
	}

	@Test
	public void testSurvivalRule1() {
		World w = new World(4);
		Badger b = new Badger(w, 2, 2, 4);
		w.grid[1][1] = new Empty(w, 1, 1);
		w.grid[1][2] = new Empty(w, 1, 2);
		w.grid[1][3] = new Empty(w, 1, 3);
		w.grid[2][1] = new Grass(w, 2, 1);
		w.grid[2][2] = b;
		w.grid[2][3] = new Grass(w, 2, 3);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Empty(w, 3, 2);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(State.EMPTY, b.next(w).who());
	}

	@Test
	public void testSurvivalRule2() {
		World w = new World(4);
		Badger b = new Badger(w, 2, 2, 0);
		w.grid[1][1] = new Fox(w, 1, 1, 0);
		w.grid[1][2] = new Fox(w, 1, 2, 0);
		w.grid[1][3] = new Fox(w, 1, 3, 0);
		w.grid[2][1] = new Grass(w, 2, 1);
		w.grid[2][2] = b;
		w.grid[2][3] = new Fox(w, 2, 3, 0);
		w.grid[3][1] = new Fox(w, 3, 1, 0);
		w.grid[3][2] = new Fox(w, 3, 2, 0);
		w.grid[3][3] = new Fox(w, 3, 3, 0);
		assertEquals(State.FOX, b.next(w).who());
	}

	@Test
	public void testSurvivalRule3() {
		World w = new World(4);
		Badger b = new Badger(w, 2, 2, 0);
		w.grid[1][1] = new Fox(w, 1, 1, 0);
		w.grid[1][2] = new Fox(w, 1, 2, 0);
		w.grid[1][3] = new Rabbit(w, 1, 3, 0);
		w.grid[2][1] = new Grass(w, 2, 1);
		w.grid[2][2] = b;
		w.grid[2][3] = new Badger(w, 2, 3, 0);
		w.grid[3][1] = new Fox(w, 3, 1, 0);
		w.grid[3][2] = new Fox(w, 3, 2, 0);
		w.grid[3][3] = new Fox(w, 3, 3, 0);
		assertEquals(State.EMPTY, b.next(w).who());
	}

	@Test
	public void testSurvivalRule4() {
		World w = new World(4);
		Badger b = new Badger(w, 2, 2, 0);
		w.grid[1][1] = new Badger(w, 1, 1, 0);
		w.grid[1][2] = new Rabbit(w, 1, 2, 0);
		w.grid[1][3] = new Rabbit(w, 1, 3, 0);
		w.grid[2][1] = new Grass(w, 2, 1);
		w.grid[2][2] = b;
		w.grid[2][3] = new Empty(w, 2, 3);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Grass(w, 3, 2);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(1, ((Badger) b.next(w)).myAge());
	}
}
