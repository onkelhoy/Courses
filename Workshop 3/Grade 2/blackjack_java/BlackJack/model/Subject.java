package BlackJack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 2016-10-28.
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state = 0;

    public void setState(int state){
        this.state = state;
        notifyAllObservers();
    }
    public int getState(){
        return state;
    }

    public void addObserver(Observer o){
        observers.add(o);
    }

    public void notifyAllObservers(){ //<- tell observers that event has been made
        //tell all observers that change has been made
        for(Observer o : observers){
            o.Notify();
        }
    }
}
