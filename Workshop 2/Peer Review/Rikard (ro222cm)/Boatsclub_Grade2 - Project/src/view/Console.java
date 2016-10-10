package view;

import java.util.Collection;
import java.util.Scanner;

import controller.Admin.ValidationType;
import model.Boat;
import model.Member;
import model.Boat.Boatstype;

public class Console implements IView {
	
	private Scanner scan;
	
	public Console(){
		scan = new Scanner(System.in);
	}

/* ******************** show ******************** */
	@Override
	public void showWelcomeMessage() {
		  System.out.println("__________________________________");
		  System.out.println("|                                 |");
		  System.out.println("| Hello, Welcome to the Boat Club |") ; 
		  System.out.println("|_________________________________|\n");
	}
	
	@Override
	public void showCompactList(Iterable<Member> members) {
		if (!members.iterator().hasNext()) System.out.println("Unfortunatelly the list is empty.");
		int counter = 1;
		for (Member m : members){
			System.out.println(counter++ +".) \t member-ID: " + m.getId());
			System.out.println("\t name: " + m.getName());
			
			// sub Iteration to count the number of boats.
			int boat_counter = getIterableSize(m.getBoats());
			System.out.println("\t boat number: " + boat_counter);
		}
	}

	@Override
	public void showVerboseList(Iterable<Member> members) {
		if (!members.iterator().hasNext()) System.out.println("Unfortunatelly the list is empty.");
		int counter = 1;
		for (Member m : members){
			System.out.print("\n" + counter++ + ".)");
			showMember(m.getId(),m.getName(),m.getPersonal_number(),m.getBoats());
		}
	}
	
	@Override
	public void showMember(int id, String name, String personal_number, Iterable<Boat> boats) {
		System.out.println("\tmember-ID: " + id);
		System.out.println("\tname: " + name);
		System.out.println("\tpersonal-number: " + personal_number + "");
		// sub Iteration to count the number of boats.
		showBoats(boats);
	}
	
	@Override
	public void showBoats(Iterable<Boat> boats) {
		int boat_counter = 1;
		System.out.println("\tboats:");
		for (Boat b : boats){
			System.out.println("\t\t" + boat_counter++ + ".) " + "Boat-ID: "  + b.getId());
			System.out.println("\t\t    " + b.getType() + " : " + b.getLength() + "m\n");
		}
	}
	
	public void showSuccessMessage(String s){
		System.out.println("***** " + s + " *****");
	}
	
	public void showErrorMessage(String s){
		System.err.println(s);
	}
	

/* ******************** select ******************** */
	
	@Override
	public int selectInstruction() { 
		System.out.println("----------- Instructions -----------");
		System.out.println("1.) Create a new member");
		System.out.println("2.) Delete a member");
		System.out.println("3.) Edit a members/boats");
		System.out.println("4.) Show members");
		return getChoice(1,4);
	}
	
	@Override
	public int selectListType() {
		System.out.println("Select a type of a list");
		System.out.println("1.) Compact list \n2.) Verbose list");
		return getChoice(1,2);
	}

	@Override
	public int selectMember() {
		System.out.println("Please type the member-id of the member you want to select "); 
		return Integer.parseInt(getInput(ValidationType.Integer));
	}
	
	@Override
	public int selectBoat() {
		System.out.println("Please type the boat-ID of the boat you want to select "); 
		return Integer.parseInt(getInput(ValidationType.Integer));
	}

	@Override
	public int selectMemberEdit() {
		System.out.println("----------- Edit Member -----------");
		System.out.println("1.) Edit name");
		System.out.println("2.) Edit personal number");
		System.out.println("3.) Edit members boats");
		System.out.println("4.) Cancel editing\n");
		return getChoice(1,4);
	}
	
	@Override
	public int selectBoatsEdit() {
		System.out.println("----------- Edit Boats  -----------");
		System.out.println("1.) Create a boat");
		System.out.println("2.) Delete a boat");
		System.out.println("3.) Edit a boat");
		System.out.println("4.) Cancel editing\n");
		return getChoice(1,4);
	}
	@Override
	public int selectSearch() {
		System.out.println("----------- Search -----------");
		System.out.println("1.) By name prefix");
		System.out.println("2.) By minimum age");
		System.out.println("3.) By birth-month");
		System.out.println("4.) By boatstype");
		System.out.println("5.) NestedSearch: (month||(name & minimumAge))");
		return getChoice(1,5);
	}
	
	public int selectMonth(){
		System.out.println("----------- Select Month -----------");
		System.out.println("1.) January");
		System.out.println("2.) February");
		System.out.println("3.) March");
		System.out.println("4.) April");
		System.out.println("5.) May");
		System.out.println("6.) June");
		System.out.println("7.) July");
		System.out.println("8.) August");
		System.out.println("9.) September");
		System.out.println("10.) October");
		System.out.println("11.) November");
		System.out.println("12.) December");
		return getChoice(1,12);
	}
	
	public int selectBoatsType(){
		System.out.println("----------- Select Boatstype -----------");
		for (int i=0;i<Boatstype.values().length;i++ ){
			System.out.println("\t" + i + ".) " + Boatstype.values()[i]);
		}
		return getChoice(0,Boatstype.values().length-1);

	}
	
/* ******************** create ******************** */
	@Override
	public Member createMember() {
		Member new_Member = new Member () ; 
		System.out.println("***** Create a new member *****") ; 
		
		System.out.println("Please enter the first name and last name"); 
		
		new_Member.setName(getInput(ValidationType.String)); //"^[a-zA-Z ]*$"));
		
		System.out.println("Please enter the personal number in this form YYMMDD-XXXX");
		new_Member.setPersonal_number(getInput(ValidationType.SwedishID));	//("^[\\d]{6}-{1}[\\d]{4}$"));
		
		return new_Member;
	}
	
	@Override
	public Boat createBoat() {
		Boat new_Boat = new Boat() ; 
		System.out.println("***** Create a new boat *****") ; 
		
		System.out.println("Please select a boatstype:");
		Boatstype t = Boatstype.values()[selectBoatsType()];
		new_Boat.setType(t);
		
		System.out.println("Please enter the length"); 
		new_Boat.setLength(Double.parseDouble(getInput(ValidationType.PositiveDouble)));
		 	
		return new_Boat;
	}
	
	
/* ******************** edit ******************** */
	public Member editMemberName(Member editMember){
		System.out.println("Please enter the new first name and last name");
		String name = getInput(ValidationType.String); 	//("^[a-zA-Z ]*$");
		editMember.setName(name);
		System.out.println("***** SUCCESSFUL CHANGED MEMBER NAME *****");		 	
		return editMember;	
	}
	
	public Member editMemberPersonalNumber(Member editMember){
		System.out.println("Please enter the new personal number in this form YYMMDD-XXXX");
		String personal_number = getInput(ValidationType.SwedishID);		// ("^[\\d]{6}-{1}[\\d]{4}$");
		editMember.setPersonal_number(personal_number);
		System.out.println("***** SUCCESSFUL CHANGED PERSONAL-NUMBER *****");		 	
		return editMember;
	}
	

	@Override
	public Boat editBoat(Boat b) {
		System.out.println("Boats-ID: " + b.getId());
		System.out.println("Current boatstype:" + b.getType());
		System.out.println("Please select a new boatstype:");
		
		Boatstype t = Boatstype.values()[selectBoatsType()];
		b.setType(t);
		
		System.out.println("Current length:" + b.getLength());
		System.out.println("Please enter the new length"); 
		b.setLength(Double.parseDouble(getInput(ValidationType.PositiveDouble)));
		return b;
	}

/* ******************** delete ******************** */
	@Override
	public boolean deleteMemberConfirmation(int id) {
		System.out.println("Are you sure to delete the member with the member-ID:" + id + "? y/N");
		char c = getInput(ValidationType.Character).charAt(0);
		while ((Character.compare(c, 'y') != 0) && (Character.compare(c, 'N') != 0)) {
			showErrorMessage("Please enter a 'y' or 'N'");
			c = getInput(ValidationType.Character).charAt(0);
		}
		
		if (Character.compare(c, 'y') == 0) return true;
		else return false;
	}
	@Override
	public boolean deleteBoatConfirmation(int id) {
		System.out.println("Are you sure to delete the boat with the Boat-ID:" + id + "? y/N");
		char c = getInput(ValidationType.Character).charAt(0);
		while ((Character.compare(c, 'y') != 0) && (Character.compare(c, 'N') != 0)) {
			showErrorMessage("Please enter a 'y' or 'N'");
			c = getInput(ValidationType.Character).charAt(0);
		}
	
		if (Character.compare(c, 'y') == 0) return true;
		else return false;
	}
/* ******************** wantsTo-Methods ******************** */
	@Override
	public boolean wantsToManage() {
		System.out.println("Press any char to contine or 'q' to quit");
		return (Character.compare(getInput(ValidationType.String).charAt(0), 'q') != 0);
	}
	
	public void closeScanner(){
		scan.close();
	}
	
/* ******************** private help functions ******************** */	
	private int getChoice(int min,int max){
		int choice;
		do {
			System.out.println("Please type a number to choose your action.");
			choice = Integer.parseInt(getInput(ValidationType.Integer));
		} while (choice<min || choice>max);
		return choice;
	}
	
	@SuppressWarnings("unused")
	private int getIterableSize(Iterable<?> values){
		if (values instanceof Collection<?>) {
			  return ((Collection<?>)values).size();
		}
		else {
			int counter = 0;
			for (Object o : values){
				counter++;
			}
			return counter;
		}
	}
	
	private String getInput(ValidationType type){
		String result = "";
		if (type == ValidationType.Integer) {
			while (result.isEmpty()){
				if (scan.hasNextInt()) result = Integer.toString(scan.nextInt());
				else {
					scan.nextLine();
					System.out.println("Please enter an Integer number");
				}
			}
		}
		else if (type == ValidationType.PositiveDouble) {
			while (result.isEmpty()){
				if (scan.hasNextDouble()) {
					Double d = scan.nextDouble();
					if (d >=0) result = Double.toString(d);
					else System.out.println("Please enter a positive number");
				}
				else {
					scan.nextLine();
					System.out.println("Please enter a number");
				}
			}
		}
		else if (type == ValidationType.Character) {
			while (result.isEmpty() && scan.hasNext()){
				result = scan.next();
			}
		}
		else if (type == ValidationType.String) {
			while (result.isEmpty() && scan.hasNext()){
				result = scan.nextLine();
			}
		}
		else if (type == ValidationType.SwedishID){
			result = scan.next();
			while (!validSwedishID(result) && scan.hasNext()){
				result = scan.next();
			}
		}
		return result;
	}
	
	private boolean validSwedishID(String s) {
		if (s.length() != 11) {
			showErrorMessage("Invalid length for a personal number!");
			return false;
		}
		String n = s.substring(0, 6) + s.substring(7, s.length());			// deleting the dash to have a clear number
		
		for (int i=0;i<n.length();i++){										// check the birth date is just out of numbers , temporary numbers are out!
			if ((Character.isDigit(n.charAt(i)) == false)) 	{
				showErrorMessage("The personal-nr. consists only digits!");
				return false;
			}
		}
		int year = Integer.parseInt(n.substring(0, 2));						// for later use for February-days per year 
		
		int month = Integer.parseInt(n.substring(2,4));
		//System.out.println("The month is: " + month);
		if ( month > 12 || month < 1) {
			showErrorMessage("Invalid month!");
			return false;
		}
		
		int day = Integer.parseInt(n.substring(4, 6));
		//System.out.println("The day is: " + day);
																			// check if the whole date is possible
		if (month == 2) {													// February extra case
			if (year%4 == 0) {
				if (day > 29 || day < 1) {
					showErrorMessage("Invalid day!");						// every 4 years February has 29 days
					return false;
				}
			}
			else if (day > 28 || day < 1) {
				showErrorMessage("Invalid day!");
				return false;
			}
		}
		else {
			if (month>7) {														// for the month after July , even month have up to 31 days
				if  (month%2 == 0) {
					if (day > 31 || day < 1) {
						showErrorMessage("Invalid day!");
						return false;
					}
				}
				else if  (day > 30 || day < 1) {
					showErrorMessage("Invalid day!");
					return false;
				}
			}
			if (month<8) {														// for the month before August, odd month have up to 31 days , except the February
				if  (month%2 == 0) {
					if (day > 30 || day < 1) {
						showErrorMessage("Invalid day!");
						return false;
					}
				}
				else if  (day > 31 || day < 1) {
					showErrorMessage("Invalid day!");
					return false;
				}
			}
		}
		
		int checksum = 0;
		int nextDigit = 0;
		for (int i=0;i<n.length()-1;i++){												// Checksum calculation
				nextDigit = Character.getNumericValue(n.charAt(i))*(2- (i%2));			// multiply the numbers by the right factorial (212121-212)
				if (nextDigit > 9) {													// if it's out of 2 digits , add both of them to one
					nextDigit = (nextDigit/10) + (nextDigit%10);
				}
				checksum += nextDigit;													// add all the 8 numbers to the sum
		}
		
		
		checksum = checksum%10;														// take the last digit
		checksum = 10 - checksum;													// subtract it from 10
		checksum = checksum%10;														// take the last digit again
		
		if (Character.getNumericValue(n.charAt(n.length()-1)) != checksum) {
			showErrorMessage("Invalid checksum!");
			return false;
		}
		
		return true;																//  if all the conditions are untrue and never returned false, it is a correct number
		
	}
	
}
	