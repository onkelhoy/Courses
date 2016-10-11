package view;

import java.io.*;
import java.util.*;

/* Extremely basic user interface. I have tried to keep everything 
 * reusable, every message that is printed is sent from the ctrl-package. */
public class TextInterface 
{
	/* Print program name and a short message */
	public static void printBanner(String prgName, String message)
	{
		System.out.println(" " + prgName);
		for (int i = prgName.length(); i > 0; i--)
			System.out.print("-");
		System.out.println();
		System.out.println(message);
	}
	
	/* Print a menu in the form of:
	 * 		QUESTION
	 * 		 - choice a
	 *       - choice b 
	 * and return whatever the user types. */
	public static int displayMenu(String question, String[] choices)
	{
		System.out.println(question);
		for (int i = 0; i < choices.length; i++)
			System.out.printf(" - %s\n", choices[i]);
			
		int choice = getChar();
		return(choice);
	}
	
	public static void displayLine(String line)
	{
		System.out.println(line);
	}
	
	public static void displayPrompt()
	{
		System.out.print("?> ");
	}
	
	public static String getLine()
	{
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		
		return(s);
	}
	
	public static int getChar()
	{
		String input = getLine();
		if (input.length() > 0)
			return((int)input.charAt(0));
		else
			return(-1);
	}
	
	public static int getInteger()
	{
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		
		return(i);
	}
}
