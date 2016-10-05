package Model;

import Helper.Database;
import org.w3c.dom.Element;

import java.util.Date;

/**
 * Created by henry on 2016-10-02.
 */
public class CalendarEvent {
    private Element event;
    public boolean dead = false;
    public CalendarEvent(Element event){
        this.event = event;
        try {
            long endTime = Long.parseLong(event.getAttribute("endTime"));
            Date now = new Date();
            Date end = new Date(endTime);

            if(now.compareTo(end) >= 0){
                // this event is now over
                dead = true;
            }
        } catch (Exception e){
            // remove event maybe
        }
    }


    public String toString(){
        try {
            Date s = new Date(Long.parseLong(event.getAttribute("startTime")));
            Date e = new Date(Long.parseLong(event.getAttribute("endTime")));
            Date c = new Date(Long.parseLong(event.getAttribute("createTime")));
            return String.format("Name: %s, Created: %s, Start: %s, End: %s, Event: %s\n", event.getAttribute("name"), c.toString(), s.toString(), e.toString(), event.getAttribute("info"));
        }
        catch (Exception e){
            return String.format("Name: %s, Created: %s, Start: %s, End: %s, Event: %s\n", event.getAttribute("name"), event.getAttribute("createTime"), event.getAttribute("startTime"), event.getAttribute("endTime"), event.getAttribute("info"));
        }
    }
    public Element getEvent() { return event; }
}
