package Model;

import Controller.YatchClub;
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
    private String name, username, identity, id, email, type;
    private Element elm;

    public Member(Element data){
        elm = data;
        username    = data.getElementsByTagName("username").item(0).getTextContent();
        name        = data.getElementsByTagName("name").item(0).getTextContent();
        identity    = data.getElementsByTagName("identity").item(0).getTextContent();
        id          = data.getElementsByTagName("id").item(0).getTextContent();
        email       = data.getElementsByTagName("email").item(0).getTextContent();
        type        = data.getAttribute("type");

        NodeList boatsxml = data.getElementsByTagName("boat");
        for(int i = 0; i < boatsxml.getLength(); i++){
            boats.add(new Boat((Element)boatsxml.item(i)));
        }
    }

    public void addBoat(Boat boat){
        boats.add(boat);
    }

    public boolean removeBoat(String boatID){
        Boat b = getBoat(boatID);
        if(b == null) return false;
        b.delete();

        boats.remove(b);
        return true;
    }
    public boolean updateBoat(String boatID, String name, String length, String type){
        Boat b = getBoat(boatID);
        if(b != null){
            b.update(name, type, length);
            if(!type.equals("") || !length.equals("")){
                b.setBerth();
            }
            return true;
        }
        return false; //cant find
    }

    public String createBoatId(){
        if(boats.size() > 0){
            int latestIndex = Integer.parseInt(boats.get(boats.size()-1).getId().substring(10));

            return id + ++latestIndex;
        }
        else return id + 0;
    }

    public void Remove(){
        elm.getParentNode().removeChild(elm);
    }

    public Iterator<Boat> getBoats(){
        return boats.iterator();
    }

    private Boat getBoat(String id){
        for (Boat b : boats) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getIdentity(){
        return identity;
    }

    public String getEmail(){
        return email;
    }

    public void setName(String name){
        this.name = name;
        elm.getElementsByTagName("name").item(0).setTextContent(name);
    }

    public boolean setUsername(String username, YatchClub club){
        //check existing
        if(club.SearchDB("//member[username = "+username+"]").getLength() == 0) {
            this.username = username;
            elm.getElementsByTagName("username").item(0).setTextContent(username);
            return true;
        }
        else return false;
    }

    public void setIdentity(String identity){
        this.identity = identity;
        elm.getElementsByTagName("identity").item(0).setTextContent(identity);
    }

    public void setEmail(String email){
        this.email = email;
        elm.getElementsByTagName("email").item(0).setTextContent(email);
    } // secerity can be aplied here

    public String getId(){
        return id;
    }
    public int getBoatSize() {return boats.size();}
}
