package edu.iastate.cs228.hw4;

/**
 *  
 * @author Caleb Utesch


 *
 */

/**
 * 
 * This class represents operators '+', '-', '*', '/', '%', '^', '(', and ')'.  
 * Every operator has an input precedence, a stack precedence, and a rank, as specified 
 * in the table below. 
 * 
 *   operator       input precedence        stack precedence       rank
 *   ___________________________________________________________________ 
 *   +  -                   1                        1              -1
 *   *  /  %                2                        2              -1
 *   ^                      4                        3              -1
 *   (                      5                       -1               0
 *   )                      0                        0               0 
 *
 */

import java.lang.Comparable;

public class Operator implements Comparable<Operator> {
	public char operator; // operator

	private int inputPrecedence; // input precedence of operator in the range
									// [0, 5]
	private int stackPrecedence; // stack precedence of operator in the range
									// [-1, 3]
	private int rank; // rank of operator (will always be -1 or 0)

	/**
	 * Constructor
	 * 
	 * @param ch
	 */
	public Operator(char ch) {
		operator = ch;

		if (operator == '+' || operator == '-') {
			inputPrecedence = 1;
			stackPrecedence = 1;
			rank = -1;
		}

		if (operator == '*' || operator == '/' || operator == '%') {
			inputPrecedence = 2;
			stackPrecedence = 2;
			rank = -1;
		}

		if (operator == '^') {
			inputPrecedence = 4;
			stackPrecedence = 3;
			rank = -1;
		}

		if (operator == '(') {
			inputPrecedence = 5;
			stackPrecedence = 3;
			rank = 0;
		}

		if (operator == ')') {
			inputPrecedence = 0;
			stackPrecedence = 0;
			rank = 0;
		}
	}

	/**
	 * Returns 1, 0, -1 if the stackPrecedence of this operator is greater than,
	 * equal to, or less than the inputPrecedence of the parameter operator op.
	 * It's for determining whether this operator on the stack should be output
	 * before pushing op onto the stack.
	 */
	@Override
	public int compareTo(Operator op) {
		if (this.stackPrecedence < op.inputPrecedence) {

			return -1;
		}

		if (this.stackPrecedence == op.inputPrecedence) {

			return 0;
		}

		else {

			return 1;
		}
	}

	/**
	 * 
	 * @return char Returns the operator character.
	 */
	public char getOp() {
		return operator;
	}

	public int getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return "" + getOp();
	}
}
