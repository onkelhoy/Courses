package Model;

import org.w3c.dom.Element;
import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Secretary extends Member {
    private ArrayList<Berth> berthAssignments = new ArrayList<Berth>();

    public Secretary(Element data) { super(data); }
}
