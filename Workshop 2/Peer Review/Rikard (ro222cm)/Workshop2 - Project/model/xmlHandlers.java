package model;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

/* This class contains handlers for the xml-parser. The details are 
 * probably unimportant for this workshop. */
public class xmlHandlers extends DefaultHandler
{
	private Member currentMember;
	private Boat currentBoat;
	private MemberList members;
	int numberOfMembers;
	
	public xmlHandlers(MemberList members)
	{
		this.members = members;
	}
	
	@Override
	public void startDocument()
	{
		
	}
	
	@Override
	public void endDocument()
	{
		
	}
	
	@Override
	public void startElement(String uri, String name, String qname, Attributes attrs)
	{
		int attrLength = 0;
		if (name.equals("memberdb"))
		{
			attrLength = attrs.getLength();
			for (int i = 0; i < attrLength; i++)
			{
				if (attrs.getQName(i).equals("membercount"))
				{
					numberOfMembers = Integer.parseInt(attrs.getValue(i));
				}
			}
		}
		else if (name.equals("member"))
		{
			currentMember = new Member();
			attrLength = attrs.getLength();
			for (int i = 0; i < attrLength; i++)
			{
				if (attrs.getQName(i).equals("name"))
				{
					String memberName = attrs.getValue(i);
					currentMember.setName(memberName);
				}
				if (attrs.getQName(i).equals("persnum"))
				{
					String num = attrs.getValue(i);
					currentMember.setPersonalNumber(num);
				}
				if (attrs.getQName(i).equals("id"))
				{
					int id = Integer.parseInt(attrs.getValue(i));
					currentMember.setID(id);
				}
			}
		}
		else if (name.equals("boat"))
		{
			currentBoat = new Boat();
			
			attrLength = attrs.getLength();
			for (int i = 0; i < attrLength; i++)
			{
				if (attrs.getQName(i).equals("type"))
				{
					switch (attrs.getValue(i).charAt(0))
					{
						case 's':
							currentBoat.setType(1);
							break;
						case 'm':
							currentBoat.setType(2);
							break;
						case 'k':
							currentBoat.setType(3);
							break;
						case 'o':
							currentBoat.setType(4);
							break;
						default:
							currentBoat.setType(4);
							break;
					}
				}
				if (attrs.getQName(i).equals("length"))
				{
					int length = Integer.parseInt(attrs.getValue(i));
					currentBoat.setLength(length);
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String name, String qname)
	{
		if (name.equals("boat"))
		{
			currentMember.addBoat(currentBoat);
		}
		else if (name.equals("member"))
		{
			members.addMember(currentMember);
		}
		else if (name.equals("memberdb"))
		{
			Member.setNumberOfMembers(numberOfMembers);
		}
		
	}
}

