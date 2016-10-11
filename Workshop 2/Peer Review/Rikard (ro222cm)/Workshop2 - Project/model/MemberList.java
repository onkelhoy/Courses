package model;

import java.util.*;

public class MemberList implements Iterable<Member>
{
	/* This is the list that will store all Member objects in the system */
	private List<Member> members;
	
	public MemberList()
	{
		members = new ArrayList<Member>();
	}
	
	public MemberList(Member m)
	{
		members = new ArrayList<Member>();
		members.add(m);
	}
	
	/* Create a Member object from two strings and add it to the list */
	public void addMember(String name, String persNumber)
	{
		if (name != null && persNumber != null)
		{
			Member m = new Member(name, persNumber);
			addMember(m);
		}
	}
	
	/* Add an existing Member object to the list */
	public void addMember(Member m)
	{
		if (m != null && members.contains(m) == false)
			members.add(m);
	}
	
	/* Remove a Member from the list */
	public void deleteMember(Member m)
	{
		members.remove(m);
	}
	
	/* List all Member objects. longFormat == true for longer listing (more info). */
	public void listMembers(boolean longFormat)
	{
		MemberInfo info = new MemberInfo();
		for (int i = 0; i < members.size(); i++)
		{
			Member m = members.get(i);
			if (m != null)
				view.TextInterface.displayLine(info.getMemberInfo(m, longFormat));
		}
	}
	
	/* Loop over the list to see if we have a member with this id. */
	public boolean isMember(int id)
	{
		for (int i = 0; i < members.size(); i++)
		{
			Member m = members.get(i);
			if (m != null)
			{
				if (m.getID() == id)
					return(true);
			}
		}
		
		return(false);
	}
	
	/* Returns the Member object with 'id' or null if none is found. */
	public Member getMember(int id)
	{
		for (int i = 0; i < members.size(); i++)
		{
			Member m = members.get(i);
			if (m != null)
			{
				if (m.getID() == id)
					return(m);
			}
		}
		
		return(null);
	}
	
	public Iterator<Member> iterator()
	{
		return(new MemberIterator());
	}
	
	private class MemberIterator implements Iterator<Member>
	{
		private int index = 0;
		
		public boolean hasNext()
		{
			return(index < members.size());
		}
		
		public Member next()
		{
			return(members.get(index++));
		}
		
		public void remove()
		{
			throw(new UnsupportedOperationException(
				"remove is not implemented"));
		}
	}
}
