package Model;

import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Boat implements Comparable<Boat> {
    // private variables
    private Berth berth;
    private String type, id, name;
    private int lenght;

    //constructor, getters and setters
    public Boat(Element data){
        String slenght  = data.getAttribute("length");
        type            = data.getAttribute("type");
        id              = data.getAttribute("id");
        name            = data.getAttribute("name");



        try{
            lenght = Integer.parseInt(slenght);
        }
        catch (Exception e){
            lenght = -1;
        }
    }


    public String getType() { return type; }
    // print out boat info
    public String toString(){
        return "";
    }
    public boolean hasPayed() {
        return berth.getSeasonPayment();
    }

    @Override
    public int compareTo(Boat o) {
        return type.compareTo(o.getType());
    }
}
