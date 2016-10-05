package Controller;

import Helper.*;
import Model.*;
import View.Menu;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
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
    private int maxlength, berthCount;

    //constructor
    public YatchClub(SeasonSimulator.Time time, int maxlength, int maxcount){
        // loading/creating the databases
        memberDB = new Database("member");
        berthDB = new Database("berth");
        calendarDB = new Database("calendar");
        paymentDB = new Database("payment");
        simulator = new SeasonSimulator(time, this);

        this.maxlength = (maxlength > 0 ? maxlength : 70); //standard 70
        berthCount = (maxcount > 0 ? maxcount : 100); //standard 100

        Menu menu = new Menu(this, new Scanner(System.in));

        berthCount -= memberDB.Search("//boat").getLength(); // deleting all taken spaces
    }

    // member
    public Member getMember() { return member; }
    public void setMember() {
        member = null;
    }

    // authenticate
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

    //boat
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
        if(berthCount <= 0) return -3; //no more room
        int length;
        try {
            length = Integer.parseInt(slength);
        }
        catch (Exception e){
            length = 0;
        }

        if(length != 0 && length <= maxlength) { // and if under certain limit set by the yatchClub
            String boatId = member.createBoatId();
            SeasonSimulator.Time time = simulator.GetSeason();

            Element boat = memberDB.getDoc().createElement("boat");
            boat.setAttribute("id", boatId);
            boat.setAttribute("name", name);
            boat.setAttribute("type", type);
            boat.setAttribute("length", slength);
            boat.setAttribute("payed", "0"); //has not payed
            boat.setAttribute("price", "0");

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
            berthCount--; // one  boat space is taken!


            member.addBoat(b);

            return 0; // it all went fine
        }
        else return -2; // length of boat was 0 or too long... (or not a valid int)
    }
    public int updateBoat(String id, String name, String type, String slength, String payed){
        int length;
        if(!slength.equals("")) {
            try {
                length = Integer.parseInt(slength);
            } catch (Exception e) {
                return -2;
            }
        }

        if(member.updateBoat(id, name, slength, type, payed)) {
            memberDB.Save();
            if(payed.equals("1")){
                //payed save to payementDB

            }
            return 0;
        }
        else return -1; //did not find
    }
    public boolean removeBoat(String id){
        if(member.removeBoat(id)) {
            berthCount++;
            memberDB.Save();
            return true;
        }
        else return false;
    }

    //databases
    public boolean RemoveNode(String expression, String db){
        switch (db.toLowerCase()){
            case "member":
                return remove(expression, memberDB);
            case "berth":
                return remove(expression, berthDB);
            case "calendar":
                return remove(expression, calendarDB);
        }
        return false; // something wierd happened
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
        }
        return null;
    }
    public void updateDB(String dbname){
        switch (dbname.toLowerCase()){
            case "member":
                memberDB.Save();
                break;
            case "calendar":
                calendarDB.Save();
                break;
            case "berth":
                berthDB.Save();
                break;
            case "payment":
                paymentDB.Save();
                break;
        }
    }
    private boolean remove(String expression, Database db){
        try {
            Node rem = db.Search(expression).item(0);
            rem.getParentNode().removeChild(rem);
            db.Save();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //calendar
    public void AddEvent(String endTime, String name, String eventInfo, String memberId){
        Element elm = createEvent(endTime, name, eventInfo, memberId);

        ((Secretary)member).addEvent(elm);
        calendarDB.Append(elm);
        calendarDB.Save();
    }
    public boolean ChangeEvent(String id, String endTime, String name, String eventInfo, String memberID) {
        try {
            new CalendarEvent((Element) calendarDB.Search(String.format("//event[@id = '%s']", id)).item(0)).Update(endTime, name, eventInfo, memberID);
            calendarDB.Save();
            return true;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    private void addEvent(Element event){
        calendarDB.getDoc().getDocumentElement().appendChild(event);
    }
    private Element createEvent(String endTime, String name, String eventInfo, String memberId){
        Element event = calendarDB.getDoc().createElement("event");
        event.setAttribute("createTime", new Date().toString());
        event.setAttribute("endTime", endTime+"");
        event.setAttribute("memberid", memberId);
        event.setAttribute("event", eventInfo);
        event.setAttribute("id", genereteId(10, false));

        event.setAttribute("name", name);

        return event;
    }


    // other
    public void newSeason(){
        //new season now.. remove all boats that hasent been payed, and make those that have been set to not payed

        NodeList members = memberDB.Search("//member");
        if(members != null) {
            for (int i = 0; i < members.getLength(); i++) {
                Element memberElm = (Element) members.item(i);
                if(memberElm.getElementsByTagName("boat").getLength() > 0) {
                    addEvent(createEvent(new Date(new Date().getTime() + 10 * 60000).toString(),
                            "New Season", "Make sure to pay your fee so we dont have to remove a boat from you!",
                            memberElm.getElementsByTagName("id").item(0).getTextContent())); //creates a event and adds it to calenderDB, event is there a full circle

                    NodeList boats = memberElm.getElementsByTagName("boat");
                    for (int j = 0; j < boats.getLength(); j++) {
                        Element boat = (Element) boats.item(j);
                        if (boat.getAttribute("payed").equals("1")) {
                            //payed
                            boat.setAttribute("payed", "0");
                        } else {
                            //not payed.. member should still pay (add a general payment for member)
                            Date end = new Date(new Date().getTime() + 10 * 60000); // 60000ms in 1min

                            addEvent(createEvent(end.toString(),"Boat Removed", String.format("Boat: (%s) removed due to fee not payed",
                                    boat.getAttribute("name")), memberElm.getElementsByTagName("id").item(0).getTextContent())); //creates a event and adds it to calenderDB

                            boat.getParentNode().removeChild(boat);
                            j--; //boat was removed
                        }
                    }
                }
            }

            memberDB.Save();
            calendarDB.Save();
        } else System.out.println("Error, cant get member list");
    }
    public int getMaxlength() { return maxlength; }
    private String genereteId(int length, boolean mem){
        char[] values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?#%&".toCharArray();
        StringBuilder strb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < length; i++){
            strb.append(values[rand.nextInt(values.length)]);
        }

        if(mem){
            if(memberDB.Search(String.format("//member[id = '%s']", strb.toString())).getLength() != 0){
                return genereteId(length, mem);
            }
        }
        else {
            NodeList ls = calendarDB.Search(String.format("//event[@id = '%s'", strb.toString()));
            if(ls != null && ls.getLength() != 0) {
                return genereteId(length, mem);
            }
        }
        return strb.toString();
    }
}