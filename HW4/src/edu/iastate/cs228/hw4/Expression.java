package edu.iastate.cs228.hw4;

/**
 * @author Caleb Utesch
 */
import java.util.HashMap;

public abstract class Expression {
	protected String postfixExpression;
	protected HashMap<Character, Integer> varTable; // hash map to store
													// variables in the

	protected Expression() {
	}

	/**
	 * Initialization with a provided hash map.
	 * 
	 * @param varTbl
	 */
	protected Expression(String st, HashMap<Character, Integer> varTbl) {
		varTable = varTbl;
	}

	/**
	 * Initialization with a default hash map.
	 * 
	 * @param st
	 */
	protected Expression(String st) {
		varTable = new HashMap<Character, Integer>();
	}

	/**
	 * @param varTbl
	 */
	public void setVarTable(HashMap<Character, Integer> varTbl) {
		this.varTable = varTbl;
	}

	/**
	 * Evaluates the infix or postfix expression.
	 * 
	 * @return value of the expression
	 * @throws ExpressionFormatException
	 *             , UnassignedVariableException
	 */
	public abstract int evaluate() throws ExpressionFormatException,
			UnassignedVariableException;

	// --------------------------------------------------------
	// Helper methods for InfixExpression and PostfixExpression
	// --------------------------------------------------------

	/**
	 * Check if a string represents an integer. You may call the static method
	 * Integer.parseInt().
	 * 
	 * @param s
	 * @return
	 */
	protected static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}

		catch (NumberFormatException n) {
			return false;
		}
	}

	/**
	 * Check if a char represents an operator, i.e., one of '+', '-', '*', '/',
	 * '%', '^'.
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%'
				|| c == '^' || c == '(' || c == ')') {
			return true;
		}

		else
			return false;
	}

	/**
	 * Check if a char is a variable, i.e., a lower case English letter.
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isVariable(char c) {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < 26; ++i) {
			if (c == alphabet[i]) {
				return true;
			} else {
			}
		}
		return false;
	}

	/**
	 * removes any extra spaces in the expression
	 * 
	 * @param s
	 * @return
	 */
	public static String removeExtraSpaces(String s) {
		return s.replaceAll("\\s+", " ").trim();

	}

}
