package Users;

import Model.Boat;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Member {
    //private variables
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private String firstname,
            lastname,
            personnumber,
            id,
            email;



    //constructor, getters and setters
    public Member(XmlElement data){

    }
    public String getId(){ return id; }


    // printing out info
    public String verboseInfo(){
        return "";
    }
    public String compactInfo(){
        return "";
    }
}
