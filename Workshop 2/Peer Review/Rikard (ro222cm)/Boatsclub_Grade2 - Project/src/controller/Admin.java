package controller;

import model.MemberList;
import view.IView;

public class Admin {
	
	private IView a_view;
	private MemberList md_list;
	private int i;					// users choice saved as int
	
	public enum ValidationType {
		SwedishID,
		PositiveDouble,
		Character,
		Integer,
		String
}
	
	public Admin(IView a_view){
		this.a_view = a_view;
		md_list = dao.MembersDAO.jaxbXMLToObject();		//  read out of XML
	}

	public void manage(){
		a_view.showWelcomeMessage();
		do {
			i = a_view.selectInstruction();
			if (i == 1) {			// create member
					md_list.addMember(a_view.createMember());
					a_view.showSuccessMessage("SUCCESSFUL CREATED A NEW MEMBER");
			}
			else if (i == 2) {		// delete member
					showList(md_list);
					if (md_list.getMemberList().iterator().hasNext()){
						int m_id = selectMember(md_list);
						showMember(m_id);
						deleteMember(m_id);
					}
			}
			else if (i == 3) {		// edit member/boats
					showList(md_list);
					if (md_list.getMemberList().iterator().hasNext()){
						int m_id = selectMember(md_list);
						showMember(m_id);
						editMember(m_id);
					}
			}
			else if (i == 4){		// list members
				showList(md_list);
				if (md_list.getMemberList().iterator().hasNext()){
					int m_id = selectMember(md_list);
					showMember(m_id);
				}				
			}
		} while (a_view.wantsToManage());
									
		dao.MembersDAO.jaxbObjectToXML(md_list);	// save data in XML
		a_view.closeScanner(); 						// close scanner for good practice
	}	
	
	private void deleteMember(int member_id){
		if (a_view.deleteMemberConfirmation(member_id)) {
			md_list.deleteMember(member_id);
			a_view.showSuccessMessage("SUCCESSFUL DELETED MEMBER ");
		}
	}
	
	private void editMember(int member_id){
		do{
			i = a_view.selectMemberEdit();
			if (i==1) md_list.editMember(a_view.editMemberName(md_list.getMember(member_id)));
			else if (i==2) md_list.editMember(a_view.editMemberPersonalNumber(md_list.getMember(member_id)));
			else if (i==3) editMembersBoats(member_id);
		} while (i != 4);
	}
	
	private void showList(MemberList list){
		i = a_view.selectListType();
		if (i == 1) a_view.showCompactList(list.getMemberList());
		else if (i == 2) a_view.showVerboseList(list.getMemberList());

	}
	
	private int selectMember(MemberList list){
		int a_member;
		do {
		a_member = a_view.selectMember();
		} while (!list.containsMember(a_member));
		return a_member;
	}
	
	private void showMember(int member_id){
		a_view.showMember(md_list.getMember(member_id).getId(),md_list.getMember(member_id).getName(),md_list.getMember(member_id).getPersonal_number(), md_list.getMember(member_id).getBoats());
	}
	
	private void editMembersBoats(int editMember_id){
		do{
			i = a_view.selectBoatsEdit();
			if (i == 1) {																// create boat
					md_list.getMember(editMember_id).addBoat(a_view.createBoat());						
					a_view.showSuccessMessage("***** SUCCESSFUL CREATED NEW BOAT *****");		 	
			}
			if (i == 2 ){																// delete boat
				if (!md_list.getMember(editMember_id).getBoats().iterator().hasNext()) a_view.showErrorMessage("WARNING: Unfortunatelly this member has no registered boats to edit/delete!");
				else {
					a_view.showBoats(md_list.getMember(editMember_id).getBoats());
					int b_id = selectBoat(editMember_id);
					if (a_view.deleteBoatConfirmation(b_id)) {
						md_list.getMember(editMember_id).deleteBoat(b_id);
						a_view.showSuccessMessage("SUCCESSFUL DELETED BOAT " + b_id);
					}
				}
			}
			if (i == 3){																// edit boat
				if (!md_list.getMember(editMember_id).getBoats().iterator().hasNext()) a_view.showErrorMessage("WARNING: Unfortunatelly this member has no registered boats to edit/delete!");
				else {
					a_view.showBoats(md_list.getMember(editMember_id).getBoats());
					int b_id = selectBoat(editMember_id);
					md_list.getMember(editMember_id).editBoat(a_view.editBoat(md_list.getMember(editMember_id).getBoat(b_id)));
					a_view.showSuccessMessage("***** SUCCESSFUL EDITED BOAT " + b_id + " *****");		 	
				}
			}
		} while(i!=4);
	}
	
	private int selectBoat(int editMember_id){
		int a_boat;
		do {
			a_boat = a_view.selectBoat();
		} while (!md_list.getMember(editMember_id).existBoat(a_boat));
		return a_boat;
	}
	
}
