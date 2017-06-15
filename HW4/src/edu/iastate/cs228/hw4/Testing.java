package edu.iastate.cs228.hw4;

public class Testing {

	public static void main(String[] args) throws Exception {
		InfixExpression exp = new InfixExpression("2 * b % 3 + ( a - ( b - ( c - d * e ) ) ) ^ ( 4 + b * ( c - d ) ) ^ ( 1 - 6 / ( i - j ) )");
		exp.postfix();
		System.out.println(exp.postfixExpression);

	}
	

}
