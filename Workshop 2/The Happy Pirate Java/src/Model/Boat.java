package Model;

import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Boat {
    // private variables
    private Berth berth;
    private String type, id, name, payed, assigned;
    private int lenght;

    //constructor, getters and setters
    public Boat(Element data){
        String slenght  = data.getAttribute("length");
        type            = data.getAttribute("type");
        id              = data.getAttribute("id");
        name            = data.getAttribute("name");
        payed           = data.getAttribute("payed");
        assigned        = data.getAttribute("assigned");

        try{
            lenght = Integer.parseInt(slenght);
        }
        catch (Exception e){
            lenght = -1;
        }
    }


    public String getType() { return type; }
    public int getLenght() { return lenght; }
    public int getTypeValue() {
        int typevalue = 2; // a normal boat such as roddbåt(dont know english name)
        switch (type.toLowerCase()){
            case "motorboat":
                return 3;
            case "sailboat":
                return 4;
            case "motorsailer":
                return 5;
            case "canoe":
            case "kayak":
                return 1;
        }

        return typevalue;
    }
    public String getId() { return id; }
    public void setBerth(Berth berth){ this.berth = berth; }

    // print out boat info
    public String toString(){
        String ans = (assigned.equals("0") ? "not assigned yet" : (hasPayed() ? "yes" : "no"));
        return String.format("Boat - Id: %s, Name: %s, Type: %s, Length: %s, Payed: %s\n", id, name, type, lenght, ans);
    }
    public boolean hasPayed() {
        return payed.equals("1");
    }

}
