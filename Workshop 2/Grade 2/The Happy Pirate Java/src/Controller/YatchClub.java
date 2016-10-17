package Controller;

import Model.*;
import View.Menu;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private Database memberDB; //berthRegistrations
    private Member member;
    private int maxlength, berthCount;

    //constructor
    public YatchClub(int maxlength, int maxcount){
        // loading/creating the databases
        memberDB = new Database("member");

        this.maxlength = (maxlength > 0 ? maxlength : 70); //standard 70
        berthCount = (maxcount > 0 ? maxcount : 100); //standard 100

        Menu menu = new Menu(this, new Scanner(System.in));

        berthCount -= memberDB.Search("//boat[@price > 0]").getLength(); // deleting all taken spaces
    }
    // member part
    public Member getMember() {
        return member;
    }

    public void setMember() {
        member = null;
    }
    // use as anonymous user
    public boolean anonymousUser(String usn){
        NodeList usnList = memberDB.Search(String.format("//member[username = '%s']", usn));
        if(usnList == null || usnList.getLength() == 0){
            return false;
        }
        else{
            Element e = (Element) usnList.item(0);
            String userName = e.getElementsByTagName("username").item(0).getTextContent();
            member = new Member(e);
            return true;
        }
    }
    // register new user
    public int register(String usn, String email, String identity){
        String id = genereteId(10, true);
        // do a valitation for identity for the 'sake of fun' - obs different countries = different identity structures

        Element m, u, e, i, s, n, a, b;
        m = memberDB.getDoc().createElement("member");
        u = memberDB.getDoc().createElement("username");
        e = memberDB.getDoc().createElement("email");
        b = memberDB.getDoc().createElement("boats");
        n = memberDB.getDoc().createElement("name");
        i = memberDB.getDoc().createElement("id");
        s = memberDB.getDoc().createElement("identity");
        a = memberDB.getDoc().createElement("address");

        u.setTextContent(usn);
        s.setTextContent(identity);
        e.setTextContent(email);
        i.setTextContent(id);


        m.appendChild(u);
        m.appendChild(e);
        m.appendChild(b);
        m.setAttribute("type", "member");
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

    // boat part
    // add a boat to db
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

            Element boat = memberDB.getDoc().createElement("boat");
            boat.setAttribute("id", boatId);
            boat.setAttribute("name", name);
            boat.setAttribute("type", type);
            boat.setAttribute("length", slength);
            boat.setAttribute("price", "0");

            Boat b = new Boat(boat);
            berthCount--; // one  boat space is taken!


            memberDB.Search(String.format("//member[id='%s']/boats", member.getId())).item(0).appendChild(boat);
            memberDB.Save();

            member.addBoat(b);

            return 0; // it all went fine
        }
        else{
            return -2;
        } // length of boat was 0 or too long... (or not a valid int)
    }
    // Updateing boat info
    public int updateBoat(String id, String name, String type, String slength){
        int length;
        if(!slength.equals("")) {
            try {
                length = Integer.parseInt(slength);
                if(length > maxlength){int error = 1/0;}
            }
            catch (Exception e) {
                return -2;
            }
        }

        if(member.updateBoat(id, name, slength, type)) {
            memberDB.Save();
            return 0;
        }
        else return -1; //did not find
    }
    // Removes a boat
    public boolean removeBoat(String id){
        if(member.removeBoat(id)) {
            berthCount++;
            memberDB.Save();
            return true;
        }
        else{
            return false;
        }
    }

    // ret Boat max length
    public int getMaxlength() {
        return maxlength;
    }

    //database part
    // search in database
    public NodeList SearchDB(String expression){
        return memberDB.Search(expression);
    }
    // saves database
    public void updateDB(){
        memberDB.Save();
    }

    // Generate user id
    private String genereteId(int length, boolean mem){
        char[] values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?#%&".toCharArray();
        StringBuilder strb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < length; i++){
            strb.append(values[rand.nextInt(values.length)]);
        }
        if(memberDB.Search(String.format("//member[id = '%s']", strb.toString())).getLength() != 0){
            return genereteId(length, mem);
        }
        return strb.toString();
    }
}