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
            Date now = new Date();
            Date end = new Date(event.getAttribute("endTime"));

            if(now.compareTo(end) >= 0){
                // this event is now over
                System.out.print(String.format("\nevent removed, start: %s, end: %s", now.toString(), end.toString()));
                event.getParentNode().removeChild(event);
                dead = true;
            }
        } catch (Exception e){
            // remove event maybe
        }
    }


    public String toString(){
        return info()+"\n\n";
    }
    public String compactInfo(){
        return info()+String.format("\n\t\tmemberID: %s, EventID: %s\n\n", event.getAttribute("memberid"), event.getAttribute("id"));
    }

    private String info() {
        String end = event.getAttribute("endTime");
        try {
            Date e = new Date(event.getAttribute("endTime"));
            end = e.toString();
        }
        catch (Exception e){ }
        return String.format("\tName: %s\n\t\tCreated: %s, End: %s\n\t\tEvent: %s", event.getAttribute("name"), event.getAttribute("createTime"), end, event.getAttribute("event"));
    }
    public void Update(String endTime, String name, String eventInfo, String memberID){
        if(!endTime.equals("")) event.setAttribute("endTime", endTime);
        if(!name.equals("")) event.setAttribute("name", name);
        if(!eventInfo.equals("")) event.setAttribute("event", eventInfo);
        if(!memberID.equals("")) event.setAttribute("memberID", memberID);
    }
    public Element getEvent() { return event; }
}
