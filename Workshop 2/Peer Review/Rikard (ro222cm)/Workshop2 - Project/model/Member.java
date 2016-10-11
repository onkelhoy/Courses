package model;

import java.util.*;

public class Member 
{
	/* This static variable is used to assign unique member id-numbers.
	 * Value from previous session is stored in a file and is used to
	 * update this number when the program is started. */
	private static int numberOfMembers = 0;
	
	private String name;
	private String persNumber; /* stored as a String */ 
	private int id;
	private LinkedList<Boat> boats; /* list of all boats this member has registered */
	
	public Member()
	{
		numberOfMembers++;
		boats = new LinkedList<Boat>();
		id = numberOfMembers;
	}
	
	public Member(String name, String persNumber)
	{
		numberOfMembers++;
		boats = new LinkedList<Boat>();
		this.name = name;
		this.persNumber = persNumber;
		id = numberOfMembers;
	}
	
	public String getName()
	{
		return(name);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPersonalNumber()
	{
		return(persNumber);
	}
	
	public void setPersonalNumber(String number)
	{
		persNumber = number;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return(id);
	}
	
	public int getNumberOfBoats()
	{
		return(boats.size());
	}
	
	public void addBoat(Boat b)
	{
		if (b != null && boats.contains(b) == false)
			boats.add(b);
	}
	
	public void addBoat(int length, int type)
	{
		boats.add(new Boat(length, type));
	}
	
	public void deleteBoat(Boat b)
	{
		if (b != null)
			boats.remove(b);
	}
	
	/* Returns info on every boat this member has as a String */
	public String boatInfo()
	{
		StringBuffer boatInfo = new StringBuffer(getName());
		boatInfo.append(" has " + getNumberOfBoats() + " boats");
		for (int i = 0; i < boats.size(); i++)
		{
			boatInfo.append("\n  " + i + ": " + boats.get(i));
		}
		
		return(boatInfo.toString());
	}
	
	public Boat getBoat(int index)
	{
		if (boats.isEmpty())
			return(null);
		
		if (index < 0 && index >= boats.size())
			return(null);
		
		return(boats.get(index));
	}
	
	/* Two static methods that accesses the static variable */
	public static void setNumberOfMembers(int n)
	{
		numberOfMembers	= n;
	}
	
	public static int getNumberOfMembers()
	{
		return(numberOfMembers);
	}
}

