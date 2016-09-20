package Main;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 2016-09-20.
 */
public class Member {
    //private variables
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private String firstname;
    private String lastname;
    private String personnumber;
    private String id;
    private String email;



    //constructor, getters and setters
    public Member(XmlElement memberData){

    }

    public String toString(){
        return "";
    }
    public String compactInfo(){

    }
}
