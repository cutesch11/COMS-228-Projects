package edu.iastate.cs228.hw4;

/**
 *  
 * @author Caleb Utesch

 *
 */

/**
 * 
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PostfixExpression extends Expression {
	private int leftOperand; // left operand for the current evaluation step
	private int rightOperand; // right operand for the current evaluation step

	private PureStack<Integer> operandStack; // stack of operands

	/**
	 * Constructor stores the input postfix string and initializes the operand
	 * stack.
	 * 
	 * @param st
	 *            input postfix string.
	 * @param varTbl
	 *            hash map that stores variables from the postfix string and
	 *            their values.
	 */
	public PostfixExpression(String st, HashMap<Character, Integer> varTbl){ 
		super(st, varTbl);
		postfixExpression = removeExtraSpaces(st);
		operandStack = new ArrayBasedStack<Integer>();
	}

	/**
	 * Constructor supplies a default hash map.
	 * 
	 * @param s
	 */
	public PostfixExpression(String s) {
		postfixExpression = removeExtraSpaces(s);
		operandStack = new ArrayBasedStack<Integer>();
		varTable = new HashMap<Character, Integer>();
	}

	/**
	 * Outputs the postfix expression according to the format in the project
	 * description. Needs to first call the method toStringHelper() from the
	 * class Expression.
	 */
	@Override
	public String toString() {
		// double Check
		return postfixExpression.replace("( ", "(").replace(" )", ")");
	}

	/**
	 * Resets the postfix expression.
	 * 
	 * @param st
	 */
	public void resetPostfix(String st) {
		postfixExpression = st;
	}

	/**
	 * Scan the postfixExpression and carry out the following:
	 * 
	 * 1. Whenever an integer is encountered, push it onto operandStack. 2.
	 * Whenever an operator is encountered, invoke it on the two elements popped
	 * from operandStack, and push the result back onto the stack. 3. On
	 * encountering a character that is not a digit, an operator, or a blank
	 * space, stop the evaluation.
	 * 
	 * @return value of the postfix expression
	 * @throws ExpressionFormatException
	 *             with one of the messages below:
	 * 
	 *             -- "Invalid character" if encountering a character that is
	 *             not a digit, an operator or a whitespace (blank, tab); --
	 *             "Too many operands" if operandStack is non-empty at the end
	 *             of evaluation; -- "Too many operators" if getOperands()
	 *             throws NoSuchElementException; -- "Divide by zero" if
	 *             division or modulo is the current operation and rightOperand
	 *             == 0; -- "0^0" if the current operation is "^" and
	 *             leftOperand == 0 and rightOperand == 0; -- self-defined
	 *             message if the error is not one of the above.
	 * 
	 *             UnassignedVariableException if the operand as a variable does
	 *             not have a value stored in the hash map. In this case, the
	 *             exception is thrown with the message
	 * 
	 *             -- "Variable <name> was not assigned a value", where <name>
	 *             is the name of the variable.
	 * @throws UnassignedVariableException
	 * 
	 */
	public int evaluate() throws ExpressionFormatException,
			UnassignedVariableException {
		Scanner s = new Scanner(postfixExpression);

		while (s.hasNext()) {
			String cur = s.next();

			if (!isInt(cur) && !isOperator(cur.charAt(0))
					&& !isVariable(cur.charAt(0))) {
				s.close();
				throw new ExpressionFormatException("Invalid Character");
			}

			if (isVariable(cur.charAt(0))) {
				if (varTable.containsKey(cur.charAt(0))) {
					operandStack.push(varTable.get(cur.charAt(0)));
				} else {
					s.close();
					throw new UnassignedVariableException("Variable"
							+ cur.charAt(0) + "was not assigned a value");
				}
			}

			else if (isInt(cur)) {
				int oper = Integer.parseInt(cur);
				operandStack.push(oper);
			}

			else if (isOperator(cur.charAt(0))) {
				try {
					getOperands();
				}

				catch (NoSuchElementException e) {
					s.close();
					throw new ExpressionFormatException("Too many operators");
				}

				if ((cur.charAt(0) == '/' || cur.charAt(0) == '%')
						&& rightOperand == 0) {
					s.close();
					throw new ExpressionFormatException("Divide by zero");
				}

				else if (cur.charAt(0) == '^' && leftOperand == 0
						&& rightOperand == 0) {
					s.close();
					throw new ExpressionFormatException("0^0");
				}
				operandStack.push(compute(cur.charAt(0)));
			}
		}

		s.close();
		int result = operandStack.pop();

		if (!operandStack.isEmpty()) {
			throw new ExpressionFormatException("Too many operands");
		}
		return result;
	}

	/**
	 * Pops the right and left operands from operandStack, and assign them to
	 * rightOperand and leftOperand, respectively. The stack must have at least
	 * two entries. Otherwise, throws NoSuchElementException.
	 */
	public void getOperands() throws NoSuchElementException {
		if (operandStack.size() < 2) {
			throw new NoSuchElementException();
		}

		rightOperand = operandStack.pop();
		leftOperand = operandStack.pop();
	}

	/**
	 * Computes "leftOperand op rightOprand".
	 * 
	 * @param op
	 *            operator that acts on leftOperand and rightOperand.
	 * @return
	 */
	public int compute(char op) {

		if (op == '+') {
			return leftOperand + rightOperand;
		}

		if (op == '-') {
			return leftOperand - rightOperand;
		}

		if (op == '*') {
			return leftOperand * rightOperand;
		}

		if (op == '/') {
			return leftOperand / rightOperand;
		}

		if (op == '%') {
			return leftOperand % rightOperand;
		}

		else {
			return (int) Math.pow(leftOperand, rightOperand);
		}
	}
}
