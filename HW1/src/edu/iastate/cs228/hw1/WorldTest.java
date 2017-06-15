package edu.iastate.cs228.hw1;

/**
 * JUnit tests for the world class
 * 
 * @author Caleb Utesch
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class WorldTest {

	@Test
	public void testFileConstruction() throws FileNotFoundException {
		World w = new World("public1.txt");
		assertEquals(State.GRASS, w.grid[0][0].who());
	}

	@Test
	public void testRandomConstruction() {
		World w = new World(4);
		w.randomInit();
		String w1 = w.toString();
		World x = new World(4);
		x.randomInit();
		String w2 = x.toString();
		assertEquals(false, w1 == w2);
	}

	@Test
	public void testWrite() throws FileNotFoundException {
		World w = new World("public1.txt");
		w.write("output.txt");
		World x = new World("output.txt");
		assertEquals(State.BADGER, x.grid[0][1].who());
	}

	@Test
	public void testToString() {
		World w = new World(1);
		w.grid[0][0] = new Badger(w, 0, 0, 0);
		assertEquals("B0" + " ", w.toString());
	}
}
