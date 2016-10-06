package Model;

import Controller.YatchClub;
import Helper.Database;
import Helper.HashAndAuth;
import Model.Boat;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by henry on 2016-09-20.
 */
public class Member {
    //private variables
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private String name, username, password, identity, id, email, type;
    private Element elm;

    public Member(Element data){
        elm = data;
        username    = data.getElementsByTagName("username").item(0).getTextContent();
        name        = data.getElementsByTagName("name").item(0).getTextContent();
        identity    = data.getElementsByTagName("identity").item(0).getTextContent();
        id          = data.getElementsByTagName("id").item(0).getTextContent();
        password    = data.getElementsByTagName("password").item(0).getTextContent();
        email       = data.getElementsByTagName("email").item(0).getTextContent();
        type        = data.getAttribute("type");

        NodeList boatsxml = data.getElementsByTagName("boat");
        for(int i = 0; i < boatsxml.getLength(); i++){
            boats.add(new Boat((Element)boatsxml.item(0)));
        }
    }

    public String getName()     { return name; }
    public String getUsername() { return username; }
    public String getIdentity() { return identity; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public Iterator<Boat> getBoats() { return boats.iterator(); }


    public void setName(String name) {
        this.name = name;
        elm.getElementsByTagName("name").item(0).setTextContent(name);
    }
    public boolean setUsername(String username, YatchClub club) {
        //check existing
        if(club.SearchDB("username = "+username, "member").getLength() == 0) {
            this.username = username;
            elm.getElementsByTagName("username").item(0).setTextContent(username);
            return true;
        }
        else return false;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
        elm.getElementsByTagName("identity").item(0).setTextContent(identity);
    }
    public void setEmail(String email) {
        this.email = email;
        elm.getElementsByTagName("email").item(0).setTextContent(email);
    } // secerity can be aplied here
    public void setPassword(String password) {
        this.password = password;
        elm.getElementsByTagName("password").item(0).setTextContent(new HashAndAuth().hash(password));
    }
    public boolean hasPayedMembership() {
        boolean pay = true; // if no boats then no payment
        for(Boat b : boats){
            pay = b.hasPayed();
            if(!pay) break;
        }

        return pay;
    }
    public int getFee() {
        int sum = 0;
        for (Boat b : boats){
            sum += Integer.parseInt(b.getFee());
            //risk **but it should only be maintained by the system therefor no string can be added here (if not someone goes in database and changes..)**
        }

        return sum;
    }
    public int getBoatCount() { return boats.size(); }


    public String getType(){ return type.toLowerCase(); }
    public String getId(){ return id; }


    // printing out info
    public String verboseInfo(){
        String idn = "";
        try {
            idn = identity.substring(0, 9);
        }
        catch (Exception e){
            idn = identity;
        }
        return String.format("USERNAME: %s\tEMAIL: %s\tNAME: %s\nID: %s\tIDENTITY: %s\n", username, email, name, id, idn);
    }
    public String compactInfo(){
        StringBuilder boatlist = new StringBuilder();
        boatlist.append("\n\tBoats:");

        for(Boat b : boats){
            boatlist.append("\n\t\t" + b.toString());
        }

        return verboseInfo() + boatlist.toString();
    }

    public void PayAll(YatchClub club){ //some sort of curency would be nice...
        for(Boat b : boats){
            if(!b.hasPayed()) {
                b.Pay();
                club.addPayment(b.getFee());
            }
        }
        club.updateDB("payment");
    }
    public void addBoat(Boat boat){ boats.add(boat); }
    public boolean removeBoat(String boatID) {
        Boat b = getBoat(boatID);
        if(b == null) return false;
        b.delete();

        boats.remove(b);
        return true;
    }
    public boolean updateBoat(String boatID, String name, String length, String type, String payed){
        Boat b = getBoat(boatID);
        if(b != null){
            b.update(name, type, length, payed);
            if(!type.equals("") || !length.equals("")){
                b.setBerth(new Berth(b, this));
            }
            return true;
        }
        return false; //cant find
    }
    private Boat getBoat(String id){
        for (Boat b : boats) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }
    public String createBoatId(){
        if(boats.size() > 0){
            int latestIndex = Integer.parseInt(boats.get(boats.size()-1).getId().substring(10));

            return id + ++latestIndex;
        }
        else return id + 0;
    }
}
