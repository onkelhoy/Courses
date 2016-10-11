package model;

public class MemberInfo 
{
	/* Create and return a String of information about a particular
	 * Member */
	public String getMemberInfo(Member m, boolean longFormat)
	{
		StringBuffer memberInfo = new StringBuffer("Name: " + m.getName());
		memberInfo.append(" (Member No. " + m.getID() + ")");
		if (longFormat)
			memberInfo.append("\nPersonal number: " + m.getPersonalNumber());
		if (longFormat)
			memberInfo.append("\n" + m.boatInfo());
		else
			memberInfo.append("\nNumber of boats: " + m.getNumberOfBoats());
		
		return(memberInfo.toString());
	}
}

