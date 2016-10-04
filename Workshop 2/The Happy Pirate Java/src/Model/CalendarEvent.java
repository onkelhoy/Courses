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
            int endTime = Integer.parseInt(event.getAttribute("endTime"));
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
    public CalendarEvent(Database db, int endTime, int startTime, String name, String memeberId, String eventInfo, String id){
        event = db.getDoc().createElement("event");
        event.setAttribute("createTime", new Date().getTime()+"");
        event.setAttribute("endTime", endTime+"");
        event.setAttribute("startTime", startTime+"");
        event.setAttribute("memberid", memeberId);
        event.setAttribute("event", eventInfo);
        event.setAttribute("id", id);

        event.setAttribute("name", name);
    }

    public void Remove(Database db){
        db.getDoc().getDocumentElement().removeChild(event);
        db.Save();
    }

    public String toString(){
        try {
            Date s = new Date(Integer.parseInt(event.getAttribute("startTime")));
            Date e = new Date(Integer.parseInt(event.getAttribute("endTime")));
            Date c = new Date(Integer.parseInt(event.getAttribute("createTime")));
            return String.format("Name: %s, Created: %s, Start: %s, End: %s, Event: %s\n", event.getAttribute("name"), c.toString(), s.toString(), e.toString(), event.getAttribute("info"));
        }
        catch (Exception e){
            return String.format("Name: %s, Created: %s, Start: %s, End: %s, Event: %s\n", event.getAttribute("name"), event.getAttribute("createTime"), event.getAttribute("startTime"), event.getAttribute("endTime"), event.getAttribute("info"));
        }
    }
    public Element getEvent() { return event; }
}
