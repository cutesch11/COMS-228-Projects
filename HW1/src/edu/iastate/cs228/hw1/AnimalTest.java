package edu.iastate.cs228.hw1;

/**
 * JUnit tests for animal class
 * 
 * @author Caleb Utesch
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class AnimalTest {

	@Test
	public void testFoxAge() {
		World w = new World(5);
		Animal a = new Fox(w, 0, 0, 2);
		assertEquals(2, a.myAge());
	}

	@Test
	public void testRabbitAge() {
		World w = new World(5);
		Animal a = new Rabbit(w, 0, 0, 1);
		assertEquals(1, a.myAge());
	}

	@Test
	public void testBadgerAge() {
		World w = new World(5);
		Animal a = new Badger(w, 0, 0, 3);
		assertEquals(3, a.myAge());
	}

}
