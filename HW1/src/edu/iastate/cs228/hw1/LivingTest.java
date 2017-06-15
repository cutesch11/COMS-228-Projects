package edu.iastate.cs228.hw1;

/**
 * JUnit tests for the Living class
 * 
 * @author Caleb Utesch
 */
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class LivingTest {

	final int badger = 0;
	final int empty = 1;
	final int fox = 2;
	final int grass = 3;
	final int rabbit = 4;

	@Test
	public void testTopLeftCorner() {
		int[] population = new int[5];
		World w = new World(5);
		w.grid[0][0] = new Badger(w, 0, 0, 0);
		w.grid[0][1] = new Badger(w, 0, 0, 0);
		w.grid[1][0] = new Fox(w, 0, 0, 1);
		w.grid[1][1] = new Fox(w, 0, 0, 0);
		w.grid[0][0].census(population);
		assertEquals(2, population[badger]);
		assertEquals(2, population[fox]);
	}

	@Test
	public void testTopEdge() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[0][2].census(population);
		assertEquals(2, population[empty]);
		assertEquals(2, population[fox]);
		assertEquals(1, population[badger]);
		assertEquals(1, population[rabbit]);
	}

	@Test
	public void testTopRightCorner() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[0][5].census(population);
		assertEquals(2, population[empty]);
		assertEquals(1, population[grass]);
		assertEquals(1, population[rabbit]);
	}

	@Test
	public void testLeftEdge() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[2][0].census(population);
		assertEquals(2, population[empty]);
		assertEquals(1, population[fox]);
		assertEquals(2, population[badger]);
		assertEquals(1, population[rabbit]);
	}

	@Test
	public void testBottomLeftCorner() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[5][0].census(population);
		assertEquals(2, population[grass]);
		assertEquals(1, population[badger]);
		assertEquals(1, population[empty]);
	}

	@Test
	public void testBottomEdge() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[5][2].census(population);
		assertEquals(3, population[empty]);
		assertEquals(2, population[grass]);
		assertEquals(1, population[badger]);
	}

	@Test
	public void testBottomRightCorner() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[5][5].census(population);
		assertEquals(2, population[empty]);
		assertEquals(2, population[rabbit]);
	}

	@Test
	public void testRightEdge() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[2][5].census(population);
		assertEquals(2, population[grass]);
		assertEquals(1, population[fox]);
		assertEquals(1, population[badger]);
		assertEquals(1, population[rabbit]);
		assertEquals(1, population[empty]);
	}

	@Test
	public void testStandardGroup() throws FileNotFoundException {
		int[] population = new int[5];
		World w = new World("public2.txt");
		w.grid[3][3].census(population);
		assertEquals(3, population[empty]);
		assertEquals(1, population[fox]);
		assertEquals(1, population[grass]);
		assertEquals(2, population[badger]);
		assertEquals(2, population[rabbit]);
	}

}
