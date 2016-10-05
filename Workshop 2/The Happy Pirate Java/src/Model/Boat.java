package Model;

import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Boat {
    // private variables
    private Berth berth;
    private String type, id, name, payed, assigned;
    private int length;
    private Element elm;

    //constructor, getters and setters
    public Boat(Element data){
        elm = data;
        String slength  = data.getAttribute("length");
        type            = data.getAttribute("type");
        id              = data.getAttribute("id");
        name            = data.getAttribute("name");
        payed           = data.getAttribute("payed");
        assigned        = data.getAttribute("assigned");

        try{
            length = Integer.parseInt(slength);
        }
        catch (Exception e){
            length = -1;
        }
    }


    public String getType() { return type; }
    public int getLength() { return length; }
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
    public void setBerth(Berth berth){ this.berth = berth; elm.setAttribute("price", berth.getFee()+""); }

    public void update(String name, String type, String length, String payed){
        if(!name.equals("")){
            elm.setAttribute("name", name);
            this.name = name;
        }
        if(!length.equals("")){
            elm.setAttribute("length", length);
            this.length = Integer.parseInt(length);
        }
        if(!type.equals("")){
            elm.setAttribute("type", type);
            this.type = type;
        }
        if(!payed.equals("")){
            elm.setAttribute("payed", payed);
            this.payed = payed;
        }
    }
    public void delete(){
        elm.getParentNode().removeChild(elm);
    }
    // print out boat info
    public String toString(){
        String ans = (assigned.equals("0") ? "not assigned yet" : (hasPayed() ? "yes" : "no"));
        return String.format("Boat - Id: %s, Name: %s, Type: %s, Length: %s, Payed: %s\n", id, name, type, length, ans);
    }
    public boolean hasPayed() {
        return payed.equals("1");
    }
}
