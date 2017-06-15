package edu.iastate.cs228.hw1;

/**
 * JUnit tests for the PredatorPrey class
 * 
 * @author Caleb Utesch
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class PredatorPreyTest {

	@Test
	public void testUpdateWorld() throws FileNotFoundException {
		World w = new World("public1.txt");
		World wnew = new World(3);
		PredatorPrey.updateWorld(w, wnew);
		PredatorPrey.updateWorld(w, wnew);
		PredatorPrey.updateWorld(w, wnew);
		PredatorPrey.updateWorld(w, wnew);
		PredatorPrey.updateWorld(w, wnew);
		assertEquals(State.FOX, wnew.grid[0][1].who());
	}

}
