package Model;

import Helper.Database;
import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Berth {
    private int fee = 0; // the cost of berth
    public int getFee() { return fee; }
    private Element elm;

    public Berth(Boat boat){
        // create a berth based on boat and have the members info

        // the price should be on some rules.. for now this will be the rule:
        // price = boat.length * 10 + boat.type * 60 - member.boatcount * 4
        if(boat.getLength() != -1) fee = boat.getLength() * 10 + boat.getTypeValue() * 100;
    }

    public Berth(Element data){
        // create a berth based on data (secretary)
        elm = data;
    }

    public String toString() {
        if(elm == null) return ""; //just in case..
        return String.format("Berth - memberID: %s, boatID: %s, Fee: %s", elm.getAttribute("memberId"), elm.getAttribute("boatId"), elm.getAttribute("fee"));
    }
}
