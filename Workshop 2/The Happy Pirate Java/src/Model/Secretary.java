package Model;

import org.w3c.dom.Element;
import java.util.ArrayList;

/**
 * Created by henry on 2016-09-20.
 */
public class Secretary extends Member {
    private ArrayList<Berth> berthAssignments = new ArrayList<Berth>();
    private ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

    public Secretary(Element data) { super(data); }
    public void addEvent(Element elm){
        CalendarEvent event = new CalendarEvent(elm);
        events.add(event);
    }
    public String printEvents() {
        return "";
    }
}
