/**
 * Created by henry on 2016-11-22.
 */
public class Chopstick {
    private boolean used = false;
    private String name;
    private Log log;

    public Chopstick(int id, Log log){
        name = "chopstick"+id;
        this.log = log;
    }

    public boolean isUsed() {return used;}
    public synchronized void take(int id){
        used = true;
        log.msg(name + " picked up");
    }
    public synchronized void release(int id){
        used = false;
        log.msg(name + " realesed");
    }
}
