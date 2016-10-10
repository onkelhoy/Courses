package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberList {
	
	@XmlElement(name="member")
	ArrayList<Member> memberList;

	public MemberList(){						// constructor
		memberList = new ArrayList<Member>();
	}
	
	public Iterable<Member> getMemberList() {	// encapsulated: getIterable
		return memberList;
	}
	
	public void addMember(Member md){
		if (memberList.size() == 0) md.setId(1);
		else md.setId(memberList.get(memberList.size()-1).getId()+1);	// unique ID = incremental counter
		memberList.add(md);
	}
	
	public void deleteMember(int member_id){
		if (!memberList.remove(getMember(member_id))) System.err.println("Member not found!");; 
	}
	
	public void editMember(Member md){			// replace member with same unique id
		memberList.set(getIndexOf(md), md);
	}
	
	public Member getMember(int id){
		for (Member m : memberList) {
			if (m.getId() == id){
				return m;
			}
		}
		return null;							// existMember before
	}
	
	public boolean containsMember(int id){
		for (Member m : memberList) {
			if (m.getId() == id){
				return true;
			}
		}
		return false;
	}
	
	
	/* ***************************** private functions ************************* */
	private int getIndexOf(Member md){
		for (int i=0;i<memberList.size();i++) {
			if (memberList.get(i).getId() == md.getId()){
				return i;
			}
		}
		return -99;							// not existing member
	}
	
}
