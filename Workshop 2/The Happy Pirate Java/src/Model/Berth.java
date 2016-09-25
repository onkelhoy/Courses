package Model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by henry on 2016-09-20.
 */
public class Berth {
    private int fee; // the cost of berth

    public Berth(Boat boat, Member member){
        // create a berth based on boat and have the members info
    }

    public Berth(XmlElement data){
        // create a berth based on data (secretary)
    }
}
