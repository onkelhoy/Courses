package Controller;

import Helper.FileHandler;
import Helper.HashAndAuth;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private HashAndAuth haa = new HashAndAuth();
    private FileHandler memberDB, calendarDB, berthDB; //berthRegistrations
    private Member member;
    public Member getMember() { return member; }
    public void setMember(boolean toNull) {
        if(toNull) member = null;
        else {
            //save dataase based on member object info
            Element e = (Element)memberDB.Search(String.format("//member[id[text() = '%s']]", member.getId())).item(0);
            e.getElementsByTagName("name").item(0).setTextContent(member.getName());
            if(memberDB.Search(String.format("//username[text() = '%s']", member.getUsername())).getLength() == 0){
                e.getElementsByTagName("username").item(0).setTextContent(member.getUsername());
            }
            e.getElementsByTagName("type").item(0).setTextContent(member.getType());
            e.getElementsByTagName("email").item(0).setTextContent(member.getEmail());
            String p = member.getPassword();
            System.out.println(p);
            try {
            if(p.substring(0, 8).equals("changed:")){
                String pass = p.substring(8);   // do a hash on this later..
                e.getElementsByTagName("password").item(0).setTextContent(haa.hash(pass));
            }} catch (IndexOutOfBoundsException ie) {}
            //e.getElementsByTagName("boats").item(0);

            memberDB.Save();
        }
    }

    public YatchClub(){
        // loading/creating the databases
        memberDB = new FileHandler("member");
        berthDB = new FileHandler("berth");
        calendarDB = new FileHandler("calendar");

        Menu menu = new Menu(this, new Scanner(System.in));
    }


    public boolean login(String usn, String pass){
        NodeList l = memberDB.Search(String.format("//member[username[text() = '%s']]", usn));
        if(l == null || l.getLength() == 0) return false;
        else {
            Element e = (Element) l.item(0);
            String dbpass = e.getElementsByTagName("password").item(0).getTextContent();
            if(haa.authenticate(pass, dbpass)) {
                member = new Member(e);
                return true; //true login success false fail'd to login.
            }
            else return false;
        }

    }

    public boolean register(String usn, String password, String email, String identity){
        String id = genereteId(10);
        // do a valitation for identity for the 'sake of fun' - obs different countries = different identity structures

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
        p.setTextContent(haa.hash(password));
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

        String format = String.format("//member[username[text() = '%s'] or email[text() = '%s'] or identity[text() = '%s']]", usn, email, identity);
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

        /*if(memberDB.Search(String.format("//member/id[text() = '%s']", strb.toString())).getLength() == 0){
            return genereteId(length);
        }*/
        return strb.toString();
    }
}
