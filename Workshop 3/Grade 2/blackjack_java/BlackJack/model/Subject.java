package BlackJack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 2016-10-28.
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state = 0;

    public void setState(int state) { this.state = state; } //notify should be called from here...
    public int getState(){
        return state;
    }

    public void subscribe(Observer o){
        observers.add(o);
    }

    public void Notify(){ //<- tell observers that event has been made
        //tell all observers that change has been made
        for(Observer o : observers){
            o.Update();
        }
    }
}
