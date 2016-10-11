package ctrl;

import java.util.*;
import static view.TextInterface.*;

public class UpdateMember 
{
	model.Member member;
	String[] memberMenuChoices = {
		"v - view information",
		"c - change information",
		"b - handle boats",
		"m - return to main menu"};
	
	String[] changeMenuChoices = {
		"n - change name",
		"p - change personal number",
		"m - return to previous menu"};
		
	String[] boatMenuChoices = {
		"r - register new boat",
		"s - select a boat to delete/update",
		"m - return to previous menu"};
	
	String[] boatTypes = {
		"s - sailboat",
		"m - motorsailer",
		"k - kayak/canoe",
		"o - other"};
		
	String[] updateBoatChoices = {
		"d - delete", 
		"u - update"};
	
	
	public UpdateMember(model.Member m)
	{
		member = m;
		displayMemberMenu();
	}
	
	private void displayMemberMenu()
	{
		int choice;
		do {
			choice = displayMenu("Managing member " + member.getName(), memberMenuChoices);
			switch (choice)
			{
				case 'v':
					model.MemberInfo info = new model.MemberInfo();
					displayLine(info.getMemberInfo(member, false));
					break;
				case 'c':
					changeMember();
					break;
				case 'b':
					handleBoats();
					break;
				default:
					break;
			}
		} while (choice != 'm');
	}
	
	/* This method is used for changing an existing members name or personal
	 * number. The membership IDs that are assigned by the system are
	 * required to be unique and thus not modifiable by the user. */ 
	private void changeMember()
	{
		int choice;
		do {
			choice = displayMenu("Select information to change about " + member.getName(), changeMenuChoices);
			switch (choice)
			{
				case 'n':
					getChar();
					displayLine("Enter the new name of the member");
					displayPrompt();
					String name = getLine();
					member.setName(name);
					break;
				case 'p':
					String pNum;
					do {
						displayLine("Enter the new personal number (YYYYMMDD-XXXX)");
						displayPrompt();
						pNum = getLine();
						Scanner sc = new Scanner(pNum);
						pNum = sc.findInLine("[0-9]{8}-[0-9]{4}");
					} while (pNum == null);
					member.setPersonalNumber(pNum);
					break;
				default:
					break;
			}
		} while (choice != 'm');
	}
	
	private void handleBoats()
	{
		int choice;
		do {
			choice = displayMenu("Select action", boatMenuChoices);
			switch (choice)
			{
				case 'r':
					addNewBoat(null);
					break;
				case 's':
					if (member.getNumberOfBoats() == 0)
					{
						displayLine("No boat registered for this member");
						break;
					}
					displayLine("Select a boat");
					displayLine(member.boatInfo());
					displayPrompt();
					
					/* make sure the choice is valid */
					int boatChoice = getInteger();
					if (boatChoice >= 0 && boatChoice < member.getNumberOfBoats()) 
					{	
						model.Boat b = member.getBoat(boatChoice);
						updateBoat(b);
					}
					break;
				default:
					break;
			}
		} while (choice != 'm');
	}
	
	private void addNewBoat(model.Boat b)
	{
		int type;
		getLine();
		int choice = displayMenu("Select action", boatTypes);
		switch (choice)
		{
			case 's':
					type = 1;
					break;
			case 'm':
					type = 2;
					break;
			case 'k':
					type = 3;
					break;
			case 'o':
					type = 4;
					break;
			default:
				return;
		}
		
		displayLine("Enter the length (in feet)");
		displayPrompt();
		if (b == null)
			member.addBoat(getInteger(), type);
		else
		{
			b.setLength(getInteger());
			b.setType(type);
		}
	}
	
	private void updateBoat(model.Boat b)
	{
		int type;
		int choice = displayMenu("Select action", updateBoatChoices);
		
		if (choice == 'd') /* delete */
		{
			member.deleteBoat(b);
			return;
		}
		
		if (choice != 'u') /* return if choice is illegal (not 'd' or 'u') */
		{
			System.err.printf("Unknown option %c\n", choice);
			return;
		}
		
		/* If we get here, choice should be 'u' (update) */
		displayLine("Enter the new length (in feet)");
		displayPrompt();
		b.setLength(getInteger());
		
		type = displayMenu("Select new type", boatTypes);
		switch (choice)
		{
			case 's':
				type = 1;
				break;
			case 'm':
				type = 2;
				break;
			case 'k':
				type = 3;
				break;
			case 'o':
				type = 4;
				break;
			default:
				type = 4;
				break;
		}
		
		b.setType(type);
	}
}

