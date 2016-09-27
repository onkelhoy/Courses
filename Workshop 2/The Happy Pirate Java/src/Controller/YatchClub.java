package Controller;

import Helper.FileHandler;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private FileHandler memberDB, calendarDB, berthDB; //berthRegistrations
    private Member member;
    public Member getMember() { return member; }
    public void setMember() { member = null; }

    public YatchClub(){
        // loading/creating the databases
        memberDB = new FileHandler("member");
        berthDB = new FileHandler("berth");
        calendarDB = new FileHandler("calendar");

        Menu menu = new Menu(this, new Scanner(System.in));
    }


    public boolean login(String usn, String pass){
        NodeList l = memberDB.Search("//member[username[text() = '"+usn+"'] and password[text() = '"+pass+"']]");
        if(l == null || l.getLength() == 0) return false;
        else {
            member = new Member((Element) l.item(0));
            return true; //true login success false fail'd to login.
        }

    }

    public boolean register(String usn, String password, String email, String identity){
        return true;
    }
}
