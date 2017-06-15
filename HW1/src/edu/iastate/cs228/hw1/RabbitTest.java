package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test cases for the Rabbit Class
 * 
 * @author Caleb Utesch
 *
 */

public class RabbitTest {

	@Test
	public void testSurvivalRule1() {
		World w = new World(4);
		Rabbit r = new Rabbit(w, 2, 2, 3);
		w.grid[1][1] = new Rabbit(w, 1, 1, 0);
		w.grid[1][2] = new Rabbit(w, 1, 2, 0);
		w.grid[1][3] = new Rabbit(w, 1, 3, 0);
		w.grid[2][1] = new Rabbit(w, 2, 1, 0);
		w.grid[2][2] = r;
		w.grid[2][3] = new Rabbit(w, 2, 3, 0);
		w.grid[3][1] = new Rabbit(w, 3, 1, 0);
		w.grid[3][2] = new Rabbit(w, 3, 2, 0);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(State.EMPTY, r.next(w).who());
	}

	@Test
	public void testSurvivalRule2() {
		World w = new World(4);
		Rabbit r = new Rabbit(w, 2, 2, 1);
		w.grid[1][1] = new Badger(w, 1, 1, 0);
		w.grid[1][2] = new Badger(w, 1, 2, 0);
		w.grid[1][3] = new Badger(w, 1, 3, 0);
		w.grid[2][1] = new Badger(w, 2, 1, 0);
		w.grid[2][2] = r;
		w.grid[2][3] = new Badger(w, 2, 3, 0);
		w.grid[3][1] = new Badger(w, 3, 1, 0);
		w.grid[3][2] = new Badger(w, 3, 2, 0);
		w.grid[3][3] = new Fox(w, 3, 3, 0);
		assertEquals(State.EMPTY, r.next(w).who());
	}

	@Test
	public void testSurvivalRule3() {
		World w = new World(4);
		Rabbit r = new Rabbit(w, 2, 2, 0);
		w.grid[1][1] = new Grass(w, 1, 1);
		w.grid[1][2] = new Fox(w, 1, 2, 0);
		w.grid[1][3] = new Fox(w, 1, 3, 0);
		w.grid[2][1] = new Badger(w, 2, 1, 0);
		w.grid[2][2] = r;
		w.grid[2][3] = new Rabbit(w, 2, 3, 0);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Fox(w, 3, 2, 0);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(State.FOX, r.next(w).who());
	}

	@Test
	public void testSurvivalRule4() {
		World w = new World(4);
		Rabbit r = new Rabbit(w, 2, 2, 0);
		w.grid[1][1] = new Empty(w, 1, 1);
		w.grid[1][2] = new Empty(w, 1, 2);
		w.grid[1][3] = new Empty(w, 1, 3);
		w.grid[2][1] = new Empty(w, 2, 1);
		w.grid[2][2] = r;
		w.grid[2][3] = new Badger(w, 2, 3, 0);
		w.grid[3][1] = new Badger(w, 3, 1, 0);
		w.grid[3][2] = new Grass(w, 3, 2);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(State.BADGER, r.next(w).who());
	}

	@Test
	public void testSurvivalRule5() {
		World w = new World(4);
		Rabbit r = new Rabbit(w, 2, 2, 0);
		w.grid[1][1] = new Grass(w, 1, 1);
		w.grid[1][2] = new Empty(w, 1, 2);
		w.grid[1][3] = new Empty(w, 1, 3);
		w.grid[2][1] = new Empty(w, 2, 1);
		w.grid[2][2] = r;
		w.grid[2][3] = new Empty(w, 2, 3);
		w.grid[3][1] = new Empty(w, 3, 1);
		w.grid[3][2] = new Empty(w, 3, 2);
		w.grid[3][3] = new Empty(w, 3, 3);
		assertEquals(1, ((Rabbit) r.next(w)).myAge());
	}

}
