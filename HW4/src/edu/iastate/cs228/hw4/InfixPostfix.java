package edu.iastate.cs228.hw4;

/**
 *  
 * @author Caleb Utesch

 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.io.File;

import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix {

	/**
	 * Repeatedly evaluates input infix and postfix expressions. See the project
	 * description for the input description. It constructs a HashMap object for
	 * each expression and passes it to the created InfixExpression or
	 * PostfixExpression object.
	 * 
	 * @param args
	 * @throws Exception
	 **/
	public static void main(String[] args) throws Exception {
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out
				.println("(Enter “I” before an infix expression, “P” before a postfix expression”)");
		System.out.println();
		Scanner s = new Scanner(System.in);
		int TrialNum = 1;
		System.out.println("Trial Number 1:");
		int InputChoice = s.nextInt();

		while (InputChoice == 1 || InputChoice == 2) {
			if (InputChoice == 1) {
				System.out.println("Expression:");
				String ex = "";
				ex += s.next();

				if (ex.charAt(0) == 'I') {
					String exp = s.nextLine();

					if (containsVariable(exp)) {// Evaluation for expression
												// that contains variables
						HashMap<Character, Integer> map = new HashMap<Character, Integer>();
						Expression in = new InfixExpression(exp, map);
						System.out.println("Infix form: " + in.toString());
						((InfixExpression) in).postfix();
						System.out.println("Postfix form: "
								+ in.postfixExpression);
						System.out.println("where");
						char[] chars = exp.toCharArray();
						for (int i = 0; i < exp.length(); ++i) {
							if (isVariable(chars[i])
									&& !in.varTable.containsKey(chars[i])) {
								System.out.println(chars[i] + " = ");
								in.varTable.put(chars[i], s.nextInt());
							}
						}
						System.out
								.println("Expression Value: " + in.evaluate());
					}

					else { // Case for if there are no variables in the
							// expression
						InfixExpression in = new InfixExpression(exp);
						System.out.println("Infix form: " + in.toString());
						in.postfix();
						System.out.println("Postfix form: "
								+ in.postfixString());
						System.out
								.println("Expression value: " + in.evaluate());
					}

				}

				else if (ex.charAt(0) == 'P') {
					String exp = s.nextLine();
					HashMap<Character, Integer> map = new HashMap<Character, Integer>();

					if (containsVariable(exp)) {
						PostfixExpression in = new PostfixExpression(exp, map);
						System.out.println("Postfix form: "
								+ in.postfixExpression);
						System.out.println("where");
						char[] chars = exp.toCharArray();
						for (int i = 0; i < exp.length(); ++i) {
							if (isVariable(chars[i])
									&& !in.varTable.containsKey(chars[i])) {
								System.out.println(chars[i] + " = ");
								in.varTable.put(chars[i], s.nextInt());
							}
						}
						System.out
								.println("Expression Value: " + in.evaluate());

					}

					else { // If theres no variables
						PostfixExpression post = new PostfixExpression(exp);
						System.out.println("Postfix form: " + post.toString());
						System.out.println(post.evaluate());
					}
				}
			}

			else if (InputChoice == 2) {
				System.out.println("Input from a file");
				System.out.println("Enter file name: ");
				String fName = s.next();
				Scanner t = new Scanner(new File(fName));
				char exChoice = t.next().charAt(0);

				while (t.hasNextLine()) {
					System.out.println();

					if (exChoice == 'I') {
						String expr = t.nextLine();

						if (containsVariable(expr)) {
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							Expression in = new InfixExpression(expr, map);
							System.out.println("Infix form: " + in.toString());
							((InfixExpression) in).postfix();
							System.out.println("Postfix form: "
									+ in.postfixExpression);
							System.out.println("where");

							int vars = numVars(expr);
							for (int i = 0; i < vars; ++i) {
								char key = t.next().charAt(0);
								t.next();
								int val = t.nextInt();
								map.put(key, val);
								System.out.println(key + " = " + map.get(key));

								if (t.hasNextLine()) {
									t.nextLine();
								}
							}
							System.out.println("Expression value: "
									+ in.evaluate());
						}

						else {
							Expression in = new InfixExpression(expr);
							System.out.println("Infix form: " + in.toString());
							((InfixExpression) in).postfix();
							System.out.println("Postfix form: "
									+ in.postfixExpression);
							System.out.println("Expression value: "
									+ in.evaluate());

							if (t.hasNextLine()) {
								t.nextLine();
							}
						}
						if (t.hasNextLine()) {
							char ch = t.next().charAt(0);
							if (ch != 'I' && ch != 'P') {
								while (ch != 'I' && ch != 'P') {
									t.nextLine();
								}
							}
							exChoice = ch;
						}
					}

					else if (exChoice == 'P') {
						String expr = t.nextLine();

						if (containsVariable(expr)) {
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							PostfixExpression post = new PostfixExpression(
									expr, map);
							System.out.println("Postfix form: "
									+ post.toString());
							System.out.println("where");

							int vars = numVars(expr);
							for (int i = 0; i < vars; ++i) {
								char key = t.next().charAt(0);
								t.next();
								int val = t.nextInt();
								map.put(key, val);
								System.out.println(key + " = " + map.get(key));

								if (t.hasNextLine()) {
									t.nextLine();
								}
							}
							System.out.println("Expression value: "
									+ post.evaluate());
						}

						else {
							PostfixExpression post = new PostfixExpression(expr);
							System.out.println("Postfix form: "
									+ post.toString());
							System.out.println("Expression value: "
									+ post.evaluate());

							if (t.hasNextLine()) {
								t.nextLine();
							}
						}
						if (t.hasNextLine()) {
							char ch = t.next().charAt(0);
							if (ch != 'I' && ch != 'P') {
								while (ch != 'I' && ch != 'P') {
									t.nextLine();
								}
							}
							exChoice = ch;
						}
					}
				}
				t.close();
			}
			TrialNum += 1;
			System.out.println();
			System.out.println("Trial Number " + TrialNum + ":");
			InputChoice = s.nextInt();
		}
		s.close();
	}

	// helper methods if needed
	private static boolean isVariable(char c) {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < 26; ++i) {
			if (c == alphabet[i]) {
				return true;
			} else {
			}
		}
		return false;
	}

	private static boolean containsVariable(String s) {
		for (int i = 0; i < s.length(); ++i) {
			if (isVariable(s.charAt(i))) {
				return true;
			} else {
			}
		}
		return false;
	}

	private static int numVars(String s) {
		int total = 0;
		char curVar = ' ';
		for (int i = 0; i < s.length(); ++i) {
			if (isVariable(s.charAt(i)) && s.charAt(i) != curVar) {
				curVar = s.charAt(i);
				total += 1;
			} else {
			}
		}
		return total;
	}

}
