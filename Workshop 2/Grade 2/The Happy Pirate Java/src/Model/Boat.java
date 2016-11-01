package Model;

import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Boat {
    // private variables
    private Berth berth;
    private String type, id, name;
    private int length;
    private Element elm;

    //constructor, getters and setters
    public Boat(Element data){
        elm = data;
        String slength  = data.getAttribute("length");
        type            = data.getAttribute("type");
        id              = data.getAttribute("id");
        name            = data.getAttribute("name");

        try{
            length = Integer.parseInt(slength);
        }
        catch (Exception e){
            length = -1;
        }

        berth = new Berth(this);
    }

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

    public void update(String name, String type, String length){
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
    }

    public void delete(){
        elm.getParentNode().removeChild(elm);
    }

    public String getId() {
        return id;
    }

    public void setBerth(){
        berth = new Berth(this);
        elm.setAttribute("price", berth.getBerthFee()+"");
    }

    public int getLength() {
        return length;
    }
    public String getType() {return type;}
    public String getName() {return name;}
    public int getFee(){return berth.getBerthFee();}
}
