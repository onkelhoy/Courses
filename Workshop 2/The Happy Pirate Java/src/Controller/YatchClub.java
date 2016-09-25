package Controller;

import Helper.FileHandler;
import Model.Member;

import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private FileHandler memberDB, brsDB, calendarDB;
    private Member member;
    public Member getMember() { return member; }
    public void setMember() { member = null; }

    public YatchClub(){
        //menu
        Menu menu = new Menu(this, new Scanner(System.in));
    }


    public boolean login(String uName, String pass){
        Member m = new Member(null);
        return true; //true login success false fail'd to login.
    }
}
