package view;

import model.Boat;
import model.Member;

public interface IView {
	// show, select, edit, delete, create
	
	int selectInstruction();						// 1)create, 2)delete or 3) edit member/boats 4) list members 5) search
	int selectListType();							// 1) compact , 2) verbose
	int selectMember();								// returns unique member-ID as String
	int selectMemberEdit();							// 1) edit name, 2) edit personal-number, 3) edit boats or 4) to quit editing
	int selectBoatsEdit();							// 1) add boat  2) delete a boat 3) edit a boat 4) quit editing 
	int selectBoat();								// returns unique boat-id
	int selectSearch();								// 1) name 2) age 3) birth-month 4) boat-type 5) NestedSearch: (month||(name & minimumAge)
	int selectMonth();								// selection from constant enum Calendar.Month -> returns index
	int selectBoatsType();							// selection from constant enum Boat.BoatType -> returns index
	
	void showWelcomeMessage();
	void showCompactList(Iterable<Member> m_it); 	// “Compact List”; name, member id and number of boats
	void showVerboseList(Iterable<Member> m_it); 	// “Verbose List”; name, personal number, member id and boats with boat information
	void showMember(int id, String name, String personal_number, Iterable<Boat> b_it);
	void showBoats(Iterable<Boat> b_it);			// show a list of all boats of the member
	void showSuccessMessage(String s);
	void showErrorMessage(String s);

	
	Member createMember();							// gets unique ID from system
	boolean deleteMemberConfirmation(int id);		// true = deletion , id = unique id of selected member
	Member editMemberName(Member editMember);
	Member editMemberPersonalNumber(Member editMember);

	Boat createBoat();
	Boat editBoat(Boat b);							// edit boats type and length, not ID!
	boolean deleteBoatConfirmation(int id);			// true = deletion , id = boat_id
	
	boolean wantsToManage();
	void closeScanner();

	
	/*	ideas for handling low level input
	boolean wantsToCreateMember();
	boolean wantsToDeleteMember();
	boolean wantsToEditMember();
	boolean wantsToShowMembers();
	boolean wantsToSearchMembers();
	boolean wantsToViewCompactList();
	boolean wantsToViewVerboseList();
	boolean wantsToEditMemberName();
	boolean wantsToEditMemberPersonalNr();
	boolean wantsToEditMemberBoats();
	boolean wantsToCreateBoat();
	boolean wantsToDeleteBoat();
	boolean wantsToEditBoat();
	/*
	public enum Events {
		CreateMember,
		DeleteMember,
		EditMember,
		ShowMembers,
		SearchMembers,
		ViewCompactList,
		ViewVerboseList,
		EditMemberName,
		EditMemberPersonalNr,
		EditMemberBoats,
		CreateBoat,
		DeleteBoat,
		EditBoat,
		SearchByNamePrefix,
		SearchByMinimumAge,
		SearchByBirthMonth,
		SearchByBoatsType,
		SearchNested,
	}
	*/
}
