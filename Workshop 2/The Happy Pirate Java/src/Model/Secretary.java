package Model;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Secretary extends Member {
    private ArrayList<Berth> berthAssignments = new ArrayList<Berth>();

    public Secretary(XmlElement data) {
        super(data);
    }
}
