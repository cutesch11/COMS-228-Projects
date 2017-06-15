package edu.iastate.cs228.hw4;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

/**
 * Some very basic tests
 * 
 * @author Yuxiang Zhang
 * 
 */
public class _JUnitTest {

	private int n = 1000; // number of test cases
	private Random r = new Random();
	private HashMap<Character, Integer> varTable = new HashMap<>();

	private void fillTable(char[] cs, int[] is) {
		if (cs.length != is.length)
			throw new IllegalArgumentException("cs.length != is.length");

		varTable.clear();
		for (int i = 0; i < cs.length; i++) {
			varTable.put(cs[i], is[i]);
		}
	}

	@Test
	public void test_infix() throws Exception {
		Expression exp = new InfixExpression("( ( a + b - c ) * d / e ) ^ 2 ^ 3 ^ 4 % 4 ^ 5");
		assertEquals("((a + b - c) * d / e) ^ 2 ^ 3 ^ 4 % 4 ^ 5", exp.toString());

		for (int i = 0; i < n; i++) {
			int a = r.nextInt(), b = r.nextInt(), c = r.nextInt(), d = r.nextInt(), e = r.nextInt();
			fillTable(new char[] { 'a', 'b', 'c', 'd', 'e' }, new int[] { a, b, c, d, e });
			exp.setVarTable(varTable);
			int expected = (int) (Math.pow((a + b - c) * d / e, 81)) % (1 << 10);
			assertEquals(expected, exp.evaluate());
		}
	}

	@Test
	public void test_postfix() throws Exception {
		Expression exp = new PostfixExpression("a b c d e + - * /");
		assertEquals("a b c d e + - * /", exp.toString());

		for (int i = 0; i < n; i++) {
			int a = r.nextInt(), b = r.nextInt(), c = r.nextInt(), d = r.nextInt(), e = r.nextInt();
			fillTable(new char[] { 'a', 'b', 'c', 'd', 'e' }, new int[] { a, b, c, d, e });
			exp.setVarTable(varTable);
			int expected = a / (b * (c - (d + e)));
			assertEquals(expected, exp.evaluate());
		}
	}

}
