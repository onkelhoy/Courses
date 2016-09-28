package Model;

import Model.Boat;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Member {
    //private variables
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private String name,
            username,
            password,
            identity,
            id,
            email,
            type;


    public String getType(){ return type.toLowerCase(); }
    //constructor, getters and setters
    public Member(Element data){
        username    = data.getElementsByTagName("username").item(0).getTextContent();
        name        = data.getElementsByTagName("name").item(0).getTextContent();
        identity    = data.getElementsByTagName("identity").item(0).getTextContent();
        id          = data.getElementsByTagName("id").item(0).getTextContent();
        password    = data.getElementsByTagName("password").item(0).getTextContent();
        email       = data.getElementsByTagName("email").item(0).getTextContent();
        type        = data.getElementsByTagName("type").item(0).getTextContent();

        NodeList boatsxml = data.getElementsByTagName("boat");
        for(int i = 0; i < boatsxml.getLength(); i++){
            boats.add(new Boat((Element)boatsxml.item(0)));
        }
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
