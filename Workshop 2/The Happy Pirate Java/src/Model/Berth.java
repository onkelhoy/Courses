package Model;

import Helper.Database;
import org.w3c.dom.Element;


/**
 * Created by henry on 2016-09-20.
 */
public class Berth {
    private int fee = 0; // the cost of berth
    public int getFee() { return fee; }

    public Berth(Boat boat, Member member){
        // create a berth based on boat and have the members info

        // the price should be on some rules.. for now this will be the rule:
        // price = boat.length * 10 + boat.type * 60 - member.boatcount * 4
        if(boat.getLenght() != -1) fee = boat.getLenght() * 10 + boat.getTypeValue() * 100 - member.getBoats().size() * 4;
    }

    public Berth(Element data, Database memberDB){
        // create a berth based on data (secretary)

        Boat boat = new Boat((Element)memberDB.Search(String.format("//boat[@id='%s']", data.getAttribute("id"))).item(0));
        Member member = new Member((Element)memberDB.Search(String.format("//member[id = '%s']", data.getAttribute("memberid"))).item(0));

        if(boat.getLenght() != -1) fee = boat.getLenght() * 10 + boat.getTypeValue() * 100 - member.getBoats().size() * 4;
    }
}
