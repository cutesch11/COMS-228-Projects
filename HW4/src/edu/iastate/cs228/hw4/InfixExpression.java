package edu.iastate.cs228.hw4;

/**
 *  
 * @author Caleb Utesch
 *
 */

import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * This class represents an infix expression. It implements infix to postfix
 * conversion using one stack, and evaluates the converted postfix expression.
 *
 */

public class InfixExpression extends Expression {
	private String infixExpression; // the infix expression to convert
	private boolean postfixReady = false; // postfix already generated if true

	private PureStack<Operator> operatorStack; // stack of operators

	/**
	 * Constructor stores the input infix string, and initializes the operand
	 * stack and the hash map.
	 * 
	 * @param st
	 *            input infix string.
	 * @param varTbl
	 *            hash map storing all variables in the infix expression and
	 *            their values.
	 */
	public InfixExpression(String st, HashMap<Character, Integer> varTbl) {
		super(st, varTbl);
		infixExpression = removeExtraSpaces(st);
		operatorStack = new ArrayBasedStack<Operator>();
	}

	/**
	 * Constructor supplies a default hash map.
	 * 
	 * @param s
	 */
	public InfixExpression(String s) {
		infixExpression = removeExtraSpaces(s);
		operatorStack = new ArrayBasedStack<Operator>();
	}

	/**
	 * Outputs the infix expression according to the format in the project
	 * description. It first calls the method toStringHelper() from the class
	 * Expression.
	 */
	@Override
	public String toString() {
		return infixExpression.replace("( ", "(").replace(" )", ")");
	}

	/**
	 * @return equivalent postfix expression, or
	 * 
	 *         a null string if a call to postfix() inside the body (when
	 *         postfixReady == false) throws an exception.
	 * @throws Exception
	 */
	public String postfixString() throws Exception {
		if (this.postfixReady == false) {
			throw new Exception("Not in postfix form yet");
		}

		return this.postfixExpression;

	}

	/**
	 * Resets the infix expression.
	 * 
	 * @param st
	 */
	public void resetInfix(String st) {
		infixExpression = st;
	}

	/**
	 * Converts infixexpression to an equivalent postfix string stored at
	 * postfixExpression. If postfixReady == false, the method scans the
	 * infixExpression, and does the following (for algorithm details refer to
	 * the relevant PowerPoint slides):
	 * 
	 * 1. Skips a whitespace character. 2. Writes a scanned operand to
	 * postfixExpression. 3. If a scanned operator has a higher input precedence
	 * than the stack precedence of the top operator on the operatorStack, push
	 * it onto the stack. 4. Otherwise, first calls outputHigherOrEqual() before
	 * pushing the scanned operator onto the stack. No push if the scanned
	 * operator is ). 5. Keeps track of the cumulative rank of the infix
	 * expression.
	 * 
	 * During the conversion, catches errors in the infixExpression by throwing
	 * ExpressionFormatException with one of the following messages:
	 * 
	 * -- "Operator expected" if the cumulative rank goes above 1; --
	 * "Operand expected" if the rank goes below 0; -- "Missing '('" if scanning
	 * a ‘)’ results in popping the stack empty with no '('; -- "Missing ')'" if
	 * a '(' is left unmatched on the stack at the end of the scan; --
	 * "Invalid character" if a scanned char is neither a digit nor an operator;
	 * 
	 * If an error is not one of the above types, throw the exception with a
	 * message you define.
	 * 
	 * Sets postfixReady to true.
	 */
	public void postfix() throws ExpressionFormatException {
		postfixExpression = "";
		Scanner s = new Scanner(infixExpression);
		int curRank = 0;

		while (s.hasNext()) {
			String current = s.next();

			if (!isInt(current) && !isVariable(current.charAt(0))
					&& !isOperator(current.charAt(0))) { // Exception Checking
				s.close();
				throw new ExpressionFormatException("Invalid character");
			}

			if (isInt(current) || isVariable(current.charAt(0))) { // Updating
																	// Rank
				curRank += 1;
			}

			if (isOperator(current.charAt(0))) { // Updating Rank
				Operator o = new Operator(current.charAt(0));
				curRank += o.getRank();
			}

			if (curRank > 1) { // Exception checking
				s.close();
				throw new ExpressionFormatException("Operator Expected");
			}

			if (curRank < 0) { // Exception checking
				s.close();
				throw new ExpressionFormatException("Operand Expected");
			}
			if (isInt(current) || isVariable(current.charAt(0))) {
				postfixExpression += current + " ";
			}

			else if (isOperator(current.charAt(0))) {
				Operator o = new Operator(current.charAt(0));

				if (operatorStack.isEmpty()) {
					operatorStack.push(new Operator(current.charAt(0)));
				}

				else if (o.operator == '(') {
					operatorStack.push(o);
				}

				else if (o.operator == ')') {
					outputHigherOrEqual(o);

					if (operatorStack.isEmpty()) {
						s.close();
						throw new ExpressionFormatException("Missing '('");
					}
					operatorStack.pop();
				}

				else if (operatorStack.peek().compareTo(o) == -1) {
					operatorStack.push(o);
				}

				else {
					outputHigherOrEqual(o);
					operatorStack.push(o);
				}

			}
		}

		if (!operatorStack.isEmpty()) {
			while (!operatorStack.isEmpty()) {
				if (operatorStack.peek().operator == '(') {
					s.close();
					throw new ExpressionFormatException("Missing ')'");
				}
				postfixExpression += operatorStack.pop() + " ";
			}
		}
		if (curRank != 1) {
			s.close();
			throw new ExpressionFormatException("Invalid Infix Expression");
		}
		s.close();
		postfixExpression = postfixExpression.trim();
		postfixReady = true;
	}

	/**
	 * This function first calls postfix() to convert infixExpression into
	 * postfixExpression. Then it creates a PostfixExpression object and calls
	 * its evaluate() method (which may throw an exception). It also passes any
	 * exception thrown by the evaluate() method of the PostfixExpression object
	 * upward the chain.
	 * 
	 * @return value of the infix expression
	 * @throws ExpressionFormatException
	 *             , UnassignedVariableException
	 * @throws UnassignedVariableException
	 */
	public int evaluate() throws ExpressionFormatException,
			UnassignedVariableException {
		this.postfix();
		PostfixExpression post = new PostfixExpression(this.postfixExpression,
				this.varTable);
		return post.evaluate();
	}

	/**
	 * Pops the operator stack and output as long as the operator on the top of
	 * the stack has a stack precedence greater than or equal to the input
	 * precedence of the current operator op. Writes the popped operators to the
	 * string postfixExpression.
	 * 
	 * If op is a ')', and the top of the stack is a '(', also pops '(' from the
	 * stack but does not write it to postfixExpression.
	 * 
	 * @param op
	 *            current operator
	 * @throws ExpressionFormatException
	 */
	private void outputHigherOrEqual(Operator op)
			throws ExpressionFormatException {
		while ((!operatorStack.isEmpty())
				&& (operatorStack.peek().compareTo(op) == 0 || operatorStack
						.peek().compareTo(op) == 1)
				&& (operatorStack.peek().operator != '(')) {
			postfixExpression += operatorStack.pop().operator + " ";
		}

	}

	// other helper methods if needed
}
