package Controller;

import Helper.FileHandler;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Random;
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
        String id = genereteId(10);
        // do a valitation for identity for the 'sake of fun'

        Element m, p, u, e, i, s, n, a, b, t;
        m = memberDB.getDoc().createElement("member");
        u = memberDB.getDoc().createElement("username");
        p = memberDB.getDoc().createElement("password");
        e = memberDB.getDoc().createElement("email");
        b = memberDB.getDoc().createElement("boats");
        t = memberDB.getDoc().createElement("type");
        n = memberDB.getDoc().createElement("name");
        i = memberDB.getDoc().createElement("id");
        s = memberDB.getDoc().createElement("identity");
        a = memberDB.getDoc().createElement("address");

        u.setTextContent(usn);
        s.setTextContent(identity);
        e.setTextContent(email);
        p.setTextContent(password);
        i.setTextContent(id);
        t.setTextContent("member");


        m.appendChild(u);
        m.appendChild(p);
        m.appendChild(e);
        m.appendChild(b);
        m.appendChild(t);
        m.appendChild(n);
        m.appendChild(i);
        m.appendChild(s);
        m.appendChild(a);

        String format = String.format("//member[username[text() = '%1s'] or email[text() = '%2s'] or identity[text() = '%3s']]", usn, email, identity);
        if(memberDB.Search(format).getLength() != 0){
            return false;
        }

        memberDB.getDoc().getDocumentElement().appendChild(m);
        memberDB.Save();
        return true;
    }

    private String genereteId(int length){
        char[] values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?#%&".toCharArray();
        StringBuilder strb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < length; i++){
            strb.append(values[rand.nextInt(values.length)]);
        }

        /*if(memberDB.Search("//member/id[text() = '"+strb.toString()+"']").getLength() == 0){
            return genereteId(length);
        }*/
        return strb.toString();
    }
}
