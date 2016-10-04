package Controller;

import Helper.*;
import Model.Berth;
import Model.Boat;
import Model.CalendarEvent;
import Model.Member;
import View.Menu;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private HashAndAuth haa = new HashAndAuth();
    private Database memberDB, calendarDB, berthDB, paymentDB; //berthRegistrations
    private Member member;
    private SeasonSimulator simulator;
    private int maxlength = 70; //standard

    public YatchClub(SeasonSimulator simulator){
        // loading/creating the databases
        this.simulator = simulator;
        memberDB = new Database("member");
        berthDB = new Database("berth");
        calendarDB = new Database("calendar");
        paymentDB = new Database("payment");

        Menu menu = new Menu(this, new Scanner(System.in));
    }

    // get and set
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

            try {
                if(p.substring(0, 8).equals("changed:")){
                    String pass = p.substring(8);   // do a hash on this later..
                    e.getElementsByTagName("password").item(0).setTextContent(haa.hash(pass));
                }} catch (IndexOutOfBoundsException ie) {}
            //e.getElementsByTagName("boats").item(0);

            memberDB.Save();
        }
    }
    public int getMaxlength() { return maxlength; }

    // methodes
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
    public int register(String usn, String password, String email, String identity){
        String id = genereteId(10, true);
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
            return -1;
        }

        memberDB.getDoc().getDocumentElement().appendChild(m);
        memberDB.Save();
        return 1;
    }

    public int saveBoat(String name, String type, String slength){
        /**
         * check season
         * if pre/off
         *   then add berth to berthDB and create boat with an empty berth
         * else assing a berth by current rules..
         */

        // create boat Node
        // assign berth based on rules
        // save DB

        // add boat to member
        //member.createBoat(new Boat(name, type, length), false);

        int length;
        try {
            length = Integer.parseInt(slength);
        }
        catch (Exception e){
            length = 0;
        }

        if(length != 0) { // and if under certain limit set by the yatchClub
            String boatId = member.createBoatId();
            SeasonSimulator.Time time = simulator.GetSeason();

            Element boat = memberDB.getDoc().createElement("boat");
            boat.setAttribute("id", boatId);
            boat.setAttribute("name", name);
            boat.setAttribute("type", type);
            boat.setAttribute("length", slength);
            boat.setAttribute("payed", "0"); //has not payed

            Boat b = new Boat(boat);
            Berth berth = new Berth(b, member);

            switch (time.name()) {
                case "season": //berth can be assigned directly
                    boat.setAttribute("price", berth.getFee()+"");
                    break;
                case "pre_season":
                case "off_season": //berth added to berthDB so secretary can assigen them
                    //add to berthDB
                    Element berthElm = berthDB.getDoc().createElement("Berth");
                    berthElm.setAttribute("memberId", member.getId());
                    berthElm.setAttribute("boatId", boatId);
                    berthElm.setAttribute("fee", berth.getFee()+""); // fee is based on certain "unknown" rules

                    berthDB.Append(berthElm);
                    berthDB.Save();
                    break;
            }

            memberDB.Search(String.format("//member[id='%s']/boats", member.getId())).item(0).appendChild(boat);
            memberDB.Save();


            //member.addBoat(b);

            return 0; // it all went fine
        }
        else return -2; // length of boat was 0 or too long... (or not a valid int)
    }
    public boolean RemoveNode(String expression, String db){
        switch (db.toLowerCase()){
            case "member":
                return remove(expression, memberDB);
            case "berth":
                return remove(expression, berthDB);
            case "calendar":
                return remove(expression, calendarDB);
            case "payment":
                return remove(expression, paymentDB);
        }
        return false; // something wierd happened
    }
    private boolean remove(String expression, Database db){
        try {
            Node rem = db.Search(expression).item(0);
            rem.getParentNode().removeChild(rem);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public NodeList SearchDB(String query, String db){
        String expression = SearchExpression.ConvertQuery(query);
        switch (db.toLowerCase()){
            case "member":
                return memberDB.Search(String.format("//member[%s]",expression));
            case "calendar":
                return calendarDB.Search(String.format("//event[%s]",expression));
            case "berth":
                return berthDB.Search(String.format("//berth[%s]",expression));
            case "payment":
                return paymentDB.Search(String.format("//payment[%s]",expression));
        }
        return null;
    }

    public void RemoveEvent(CalendarEvent event){
        event.Remove(calendarDB);
    }
    public void AddEvent(int endTime, int startTime, String name, String eventInfo, String memberId){
        CalendarEvent event = new CalendarEvent(calendarDB, endTime, startTime, name, memberId, eventInfo, genereteId(10, false));
        calendarDB.Append(event.getEvent());
        calendarDB.Save();
    }
    public void ChangeEvent(String id, int endTime, int startTime, String name, String eventInfo, String memberID) {

    }

    private String genereteId(int length, boolean mem){
        char[] values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?#%&".toCharArray();
        StringBuilder strb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < length; i++){
            strb.append(values[rand.nextInt(values.length)]);
        }

        if(mem && memberDB.Search(String.format("//member[id = '%s']", strb.toString())).getLength() != 0){
            return genereteId(length, mem);
        }
        /*else if(calendarDB.Search(String.format("//event[@id = '%s'", strb.toString())).getLength() != 0){
            return genereteId(length, mem);
        }*/
        return strb.toString();
    }
}