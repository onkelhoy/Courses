package Model;

import Helper.Database;
import Model.Boat;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Member {
    //private variables
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private Database db;
    private String name, username, password, identity, id, email, type;

    public String getName()     { return name; }
    public String getUsername() { return username; }
    public String getIdentity() { return identity; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public ArrayList<Boat> getBoats() { return boats; }


    public void setName(String name)            { this.name = name; }
    public void setUsername(String username)    { this.username = username; }
    public void setIdentity(String identity)    { this.identity = identity; }
    public void setEmail(String email)          { this.email = email; } // secerity can be aplied here
    public void setPassword(String password)    { this.password = "changed:"+password; }
    public boolean hasPayedMembership() {
        boolean pay = true; // if no boats then no payment
        for(Boat b : boats){
            pay = b.hasPayed();
            if(!pay) break;
        }

        return pay;
    }

    public String getType(){ return type.toLowerCase(); }
    public String getId(){ return id; }

    public Member(Element data){
        this.db = db;
        username    = data.getElementsByTagName("username").item(0).getTextContent();
        name        = data.getElementsByTagName("name").item(0).getTextContent();
        identity    = data.getElementsByTagName("identity").item(0).getTextContent();
        id          = data.getElementsByTagName("id").item(0).getTextContent();
        password    = data.getElementsByTagName("password").item(0).getTextContent();
        email       = data.getElementsByTagName("email").item(0).getTextContent();
        type        = data.getElementsByTagName("type").item(0).getTextContent();

        NodeList boatsxml = data.getElementsByTagName("boat");
        for(int i = 0; i < boatsxml.getLength(); i++){
            boats.add(new Boat((Element)boatsxml.item(0)));
        }
    }

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
        return "";
    }

    public void addBoat(Boat boat){ boats.add(boat); }
    public String createBoatId(){
        if(boats.size() > 0){
            int latestIndex = Integer.parseInt(boats.get(boats.size()-1).getId().substring(10));

            return id + ++latestIndex;
        }
        else return id + 0;
    }
}
