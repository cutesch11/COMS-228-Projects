package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Random;

public class Testing {

	public static void main(String[] args) throws InputMismatchException, FileNotFoundException 
	{
		QuickSorter select = new QuickSorter("points.txt");
		System.out.println(select.toString());
		select.sort(0);
		System.out.println(select.stats());
	}
}
