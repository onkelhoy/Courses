package ctrl;

import static view.TextInterface.*;
import java.util.*;

public class RegistryCtrl 
{
	private model.MemberList members;
	private UpdateMember update;
	private model.MemberDB db;
	private String[] menuChoices = {
		"a - Add member", "l - List members", "e - list members (extended)",
		"m - Manage a member", "d - delete a member", "q - Quit"};
	
	public RegistryCtrl()
	{
		/* Create a new MemberList that encapsulates an ArrayList of
		 * members. */
		members = new model.MemberList();
		/* Print the name of the program and some information */
		printBanner("Registry v0.10", "Program for Workshop 2 (1dv607)");
		
		/* This section creates an object of class MemberDB and passes
		 * the member list to it. MemberDB containts methods to read
		 * the xml-file that stores members from previous sessions. */
		db = new model.MemberDB(members);
		db.loadDB();
		
		/* This is the main loop of the program */
		manageRegistry();
	}
	
	private void manageRegistry()
	{
		
		int choice = displayMenu("Choose an action:", menuChoices);
		while (choice != 'q')
		{
			switch (choice)
			{
				case 'a':
					addNewMember();
					break;
				case 'l': /* list members */
					members.listMembers(false);
					break;
				case 'e': /* list members, comprehensive version */
					members.listMembers(true);
					break;
				case 'm':
					manageMember();
					break;
				case 'd':
					deleteMember();
					break;
				default:
					break;
			}
			
			/* get the users choice */
			choice = displayMenu("Choose an action:", menuChoices);
		}
		
		
		/* The user has pressed 'q' to quit. Save the list of members */
		db.saveDB();
	}
	
	private void addNewMember()
	{
		/* Get the name of the new member */
		getChar();
		displayLine("Enter the full name of the new member");
		displayPrompt();
		String name = getLine();
		
		/* Get the personal number */
		String pNum;
		do {
			displayLine("Enter the personal number of the new member (YYYYMMDD-XXXX)");
			displayPrompt();
			pNum = getLine();
			Scanner sc = new Scanner(pNum);
			/* make sure the format is correct or loop again */
			pNum = sc.findInLine("[0-9]{8}-[0-9]{4}");
		} while (pNum == null);
		
		/* add the new member to the list */
		members.addMember(name, pNum);
	}
	
	/* Method to locate and return a certain member in the list of members */
	private model.Member findMember()
	{
		String inputLine;
		int id;
		Scanner sc;
		
		getChar();
		while (true)
		{
			displayLine("Enter membership ID of the member");
			displayPrompt();
			inputLine = getLine();
			sc = new Scanner(inputLine);
			if (sc.hasNextInt())
			{
				id = sc.nextInt();
				break;
			}
		}
		
		if (members.isMember(id))
			return(members.getMember(id));
		else
			return(null);
	}
	
	/* This method creates an object of the class UpdateMember, which is 
	 * used for manipulation of Member objects. The reason for creating 
	 * another class in the ctrl-package is that manipulating members
	 * and boats required a lot of hardcoded strings for the various menus, 
	 * that would have cluttered this class. */
	private void manageMember()
	{
		/* locate the member */
		model.Member m = findMember();
		
		if (m != null)
		{
			update = new UpdateMember(m);
		}
	}
	
	private void deleteMember()
	{
		model.Member m = findMember();
		
		if (m != null)
		{
			members.deleteMember(m);
		}
	}
}

